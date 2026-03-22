import java.util.Arrays;

public class KarnaughMap 
{
    private int numberOfVariables;
    private Boolean[][][] map;

    public KarnaughMap(int numberOfVariables)
    {
        this.numberOfVariables = numberOfVariables;
        
        int numberOfRowVariables = numberOfVariables / 2;
        int numberOfColumnVariables = numberOfVariables % 2 == 0 ? numberOfVariables / 2 : numberOfVariables / 2 + 1;
        int numberOfRows = (int) Math.pow(2, numberOfRowVariables);
        int numberOfColumns = (int) Math.pow(2, numberOfColumnVariables);

        Boolean[][] rowsGrayCode = makeGrayCode(numberOfRowVariables);
        Boolean[][] columnsGrayCode = makeGrayCode(numberOfColumnVariables);

        this.map = new Boolean[numberOfRows][numberOfColumns][numberOfVariables];
        for(int i = 0; i < map.length; i++)
        {
            for(int j = 0; j < map[0].length; j++)
            {
                for(int k = 0; k < map[0][0].length; k++)
                {
                    if(k < numberOfRowVariables)
                    {
                        map[i][j][k] = rowsGrayCode[i][k];
                    }
                    else
                    {
                        map[i][j][k] = columnsGrayCode[j][k - numberOfRowVariables];
                    }  
                }
            }
        }
    }
    public void printMapAgainstAllKeys()
    {
        int[][] keys = makeKeys(numberOfVariables);
        for(int i = 0; i < keys.length; i++)
        {
            int[] key = keys[i];
            System.out.println(Arrays.toString(key));
            printMapAgainstKey(key);
            System.out.println();
        }
    }
    public int[][] makeKeys(int numberOfVariables)
    {
        int[][] keys = 
        {
            {0},
            {1},
            {-1}
        };
        for(int i = 2; i <= numberOfVariables; i++)
        {
            keys = incrementKeys(keys);
        }
        return keys;
    }
    public int[][] incrementKeys(int[][] keys)
    {
        int[][] unconsidered = new int[keys.length][keys[0].length + 1];
        for(int i = 0; i < keys.length; i++)
        {
            for(int j = 0; j < keys[0].length; j++)
            {
                
                unconsidered[i][j] = keys[i][j];
            }
            unconsidered[i][unconsidered[0].length - 1] = 0;
        }
        int[][] trues = new int[keys.length][keys[0].length + 1];
        for(int i = 0; i < keys.length; i++)
        {
            for(int j = 0; j < keys[0].length; j++)
            {
                trues[i][j] = keys[i][j];
            }
            trues[i][trues[0].length - 1] = 1;
        }
        int[][] falses = new int[keys.length][keys[0].length + 1];
        for(int i = 0; i < keys.length; i++)
        {
            for(int j = 0; j < keys[0].length; j++)
            {
                falses[i][j] = keys[i][j];
            }
            falses[i][falses[0].length - 1] = -1;
        }
        int[][] newKeys = new int[keys.length * 3][keys[0].length + 1];
        for(int i = 0; i < newKeys.length; i++)
        {
            if(i < unconsidered.length)
            {
                newKeys[i] = unconsidered[i];
            }
            else if(i < unconsidered.length + trues.length)
            {
                newKeys[i] = trues[i - unconsidered.length];
            }
            else
            {
                newKeys[i] = falses[i - unconsidered.length - trues.length];
            }
        }
        return newKeys;
    }
    public void printMapAgainstKey(int[] key)
    {
        for(int i = 0; i < map.length; i++)
        {
            for(int j = 0; j < map[0].length; j++)
            {
                if(checkValueAgainstKey(map[i][j], key))
                {
                    System.out.print("X");
                }
                else
                {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
    }
    public Boolean checkValueAgainstKey(Boolean[] value, int[] key)
    {
        Boolean result = true;
        for(int i = 0; i < key.length; i++)
        {
            if(key[i] == 0 || (key[i] == 1 && value[i] == true) || (key[i] == -1 && value[i] == false))
            {
                result = true;
            }
            else
            {
                return false;
            }
        }
        return result;
    }

    public void print()
    {
        for(int i = 0; i < map.length; i++)
        {
            for(int j = 0; j < map[0].length; j++)
            {
                System.out.print(Arrays.deepToString(map[i][j]));
            }
            System.out.println();
        }
    }
    public Boolean[][] makeGrayCode(int numberOfVariables)
    {
        Boolean[][] grayCode = 
        {
            {false},
            {true}
        };
        for(int i = 1; i < numberOfVariables; i++)
        {
            grayCode = incrementGrayCode(grayCode);
        }
        return grayCode;
    }
    public Boolean[][] incrementGrayCode(Boolean[][] grayCode)
    {
        Boolean[][] forwardGrayCode = new Boolean[grayCode.length][grayCode[0].length + 1];
        for(int i = 0; i < forwardGrayCode.length; i++)
        {
            for(int j = forwardGrayCode[0].length - 1; j > 0; j--)
            {
                forwardGrayCode[i][j] = grayCode[i][j - 1];
            }
            forwardGrayCode[i][0] = false;
        }
        Boolean[][] backwardGrayCode = new Boolean[grayCode.length][grayCode[0].length + 1];
        for(int i = 0; i < backwardGrayCode.length; i++)
        {
            for(int j = backwardGrayCode[0].length - 1; j > 0; j--)
            {
                backwardGrayCode[grayCode.length - i - 1][j] = grayCode[i][j - 1];
            }
            backwardGrayCode[i][0] = true;
        }
        Boolean[][] newGrayCode = new Boolean[grayCode.length * 2][grayCode[0].length + 1];
        for(int i = 0; i < newGrayCode.length; i++)
        {
            if(i < forwardGrayCode.length)
            {
                newGrayCode[i] = forwardGrayCode[i];
            }
            else
            {
                newGrayCode[i] = backwardGrayCode[i - forwardGrayCode.length];
            }
        }
        return newGrayCode;
    }
}