import re
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
    filename = "p1err.in"
    tokens_file = "tokens.in"
    tokens = get_tokens(tokens_file)

    within_string = False
    entire_string = ""

    with open(filename, 'r') as file:
        line_nr = 0

        for line in file:
            line_nr += 1

            # avoid issues with escape characters
            line.replace('\r', '')

            # split the line by each symbol
            spline = re.split('( |,|\.|~|\"|<=|=>|=|%|<|>|{|}|\[|]|\(|\)|:=)', line)

            for token in spline:
                # check if the string was finished on the same line
                if within_string and token == ".":
                    within_string = False
                    print(f"Lexical error on line {line_nr} at token \"{entire_string}\". String was not closed.")

                # if we're within a string and still haven't reached its end, we add the token to the string
                elif within_string and token != "\"":
                    entire_string += token

                # move to the next line if we find a comment
                elif token == "~":
                    break

                # add token to PIF if it is a reserved word or symbol
                elif token in tokens:
                    pif.genPIF(token, 0)

                # pass if we find a space outside a string
                elif within_string is False and token == " ":
                    pass

                elif token == "\"":
                    # if we find a delimiter and we're not within the string, that means the string starts now
                    if within_string is False:
                        within_string = True

                    # but if we are already within the string, that means the string is over so we add it to ST and PIF
                    else:
                        st.insert(entire_string)
                        idx = st.find(entire_string)
                        pif.genPIF(entire_string, idx)
                        within_string = False
                        entire_string = ""

                # ignore spaces and empty strings
                elif token in ['', '\t', '\n']:
                    pass

                # if the token is an identifier
                elif re.match('^[a-zA-Z]+$', token):
                    st.insert(token)
                    idx = st.find(token)
                    pif.genPIF(token, idx)

                # if the token is an integer constant
                elif re.match('^-?[1-9]\d+$', token):
                    st.insert(token)
                    idx = st.find(token)
                    pif.genPIF(token, idx)

                else:
                    print(f"Lexical error on line {line_nr} at token {token}")

    with open("pif.out", 'w') as file:
        file.write(pif.__str__())

    with open("st.out", 'w') as file:
        file.write(st.__str__())

