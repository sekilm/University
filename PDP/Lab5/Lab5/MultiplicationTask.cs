namespace Lab5
{
    public class MultiplicationTask
    {
        private int start;
        private int end;
        private Polynomial p, q, result;

        public MultiplicationTask(int start, int end, Polynomial p, Polynomial q, Polynomial result)
        {
            this.start = start;
            this.end = end;
            this.p = p;
            this.q = q;
            this.result = result;
        }

        public void run()
        {
            for (int i = start; i < end; i++)
            {
                if (i > result.getLength())
                    return;
                for (int j = 0; j < i; j++)
                {
                    if (j < p.getLength() && (i - j) < q.getLength())
                    {
                        int value = p.getCoefficients()[j] * q.getCoefficients()[i - j];
                        result.getCoefficients()[i] += value;
                    }
                }
            }
        }
    }
}