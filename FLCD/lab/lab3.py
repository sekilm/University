import re
from lab2 import HashTable
from PIF import PIF
from ST import SymbolTable


# function that puts the tokens into a list
def get_tokens(filename):
    tokens = []

    with open(filename, 'r') as file:
        for line in file:
            line.replace('\r', '')
            for token in line.split('\n'):
                tokens.append(token)

    return tokens


if __name__ == '__main__':
    pif = PIF()
    st = SymbolTable()
    filename = "p1.in"
    tokens_file = "tokens.in"
    tokens = get_tokens(tokens_file)

    within_string = False
    string_delimiter = '"'
    entire_string = ''

    with open(filename, 'r') as file:
        line_nr = 0
        for line in file:
            line_nr += 1

            # avoid issues with escape characters
            line.replace('\r', '')

            # split the line by each symbol
            spline = re.split('( |,|\.|\+|-|~|\*|\"|=|%|<|>|\\|/|{|}|\[|]|\(|\)|:)', line)
            for token in spline:
                # move to the next line if we find a comment
                if token == '~':
                    break

                # ignore spaces and empty strings
                elif token in ['', '\t']:
                    pass

                # add spaces or newlines to strings if we're within a string
                elif token in ['\n', ' ']:
                    if within_string is False:
                        pass
                    else:
                        entire_string += token

                # add token to PIF if it is a reserved word or symbol
                elif token in tokens:
                    pif.genPIF(token, 0)

                # if we're within a string and still haven't reached its end, we add the token to the string
                elif within_string and token != string_delimiter:
                    entire_string += token

                elif token == string_delimiter:
                    # if we find a delimiter and we're not within the string, that means the string starts now
                    if within_string is False:
                        entire_string = token
                        within_string = True

                    # but if we are already within the string, that means the string is over so we add it to ST and PIF
                    else:
                        entire_string += token
                        st.insert(entire_string)
                        idx = st.find(entire_string)
                        pif.genPIF(entire_string, idx)
                        within_string = False

                # if the token is an identifier
                elif re.match('^[a-zA-Z]*$', token):
                    st.insert(token)
                    idx = st.find(token)
                    pif.genPIF(token, idx)

                # if the token is an integer constant
                elif re.match('-?^\d+$', token):
                    st.insert(token)
                    idx = st.find(token)
                    pif.genPIF(token, idx)

                else:
                    print(f"Lexical error on line {line_nr} at token {token}")

    with open("pif.out", 'w') as file:
        file.write(pif.__str__())

    with open("st.out", 'w') as file:
        file.write(st.__str__())

