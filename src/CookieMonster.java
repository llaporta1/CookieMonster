import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.*;
import java.io.File;

/* YOU ARE ALLOWED (AND EXPECTED) TO USE THE JAVA ARRAYDEQUE CLASS */

public class CookieMonster {

    private int [][] cookieGrid;
    private int numRows;
    private int numCols;

    public CookieMonster(int [][] cookieGrid) {
        this.cookieGrid = cookieGrid;
        this.numRows    = cookieGrid.length;
        this.numCols    = cookieGrid[0].length;
    }

    private boolean goodPoint(int row, int col) {
        return (row >= 0 && row < numRows && col >= 0 && col < numCols && cookieGrid[row][col] >= 0);
    }

    /* RECURSIVELY calculates the route which grants the most cookies. */
    public int recursiveCookies() {
        return recursiveOptimalPath(0, 0);
    }

    /* Helper function for the above, which returns the maximum number of cookies edible starting at coordinate (row, col). */
    /* From any given position, always check right before checking down */

    private int recursiveOptimalPath(int row, int col) {
        int sum=0;
        if (!goodPoint(row,col))
        {
            return sum;
        }
        sum = cookieGrid[row][col];
        int a = recursiveOptimalPath(row+1,col);
        int b = recursiveOptimalPath(row,col+1);
        if (a>b)
        {
            sum += a;
        }
        else if (b>=a)
        {
            sum += b;
        }
        return sum;
    }

    /* Calculate which route grants the most cookies using a queue. */
    /* From any given position, always add the path right before adding the path down */
    public int queueCookies() {
        ArrayDeque<PathMarker> queue = new ArrayDeque<PathMarker>();
        int mostCookiesSoFar = cookieGrid[0][0];
        queue.addFirst(new PathMarker(0, 0, cookieGrid[0][0]));
        if (cookieGrid[0][0] == -1)
        {
            return 0;
        }
        while (!queue.isEmpty())
        {
            PathMarker p = queue.pop();
            if (goodPoint(p.row,p.col+1))
            {
                PathMarker b = new PathMarker(p.row,p.col+1,p.total + cookieGrid[p.row][p.col+1]);
                queue.addFirst(b);
                if (b.total>mostCookiesSoFar)
                {
                    mostCookiesSoFar = b.total;
                }
            }
            if (goodPoint(p.row+1,p.col))
            {
                PathMarker a = new PathMarker(p.row+1,p.col,p.total + cookieGrid[p.row+1][p.col]);
                queue.addFirst(a);
                if (a.total>mostCookiesSoFar)
                {
                    mostCookiesSoFar = a.total;
                }
            }
        }
        return mostCookiesSoFar;
        //remove that and process if most cookies so far then go in right direction add to queue if processes
        // removes last thing at end
    }

    /* Calculate which route grants the most cookies using a stack. */
    /* From any given position, always add the path right before adding the path down */
    public int stackCookies() { //depth
        ArrayDeque<PathMarker> stack = new ArrayDeque<PathMarker>(); //double ended queue google methods
        int mostCookiesSoFar = cookieGrid[0][0];
        stack.addFirst(new PathMarker(0, 0, cookieGrid[0][0]));
        if (cookieGrid[0][0] == -1)
        {
            return 0;
        }
        while (!stack.isEmpty())
        {
            PathMarker p = stack.pop();
            if (goodPoint(p.row,p.col+1))
            {

                PathMarker a = new PathMarker(p.row,p.col+1,p.total+ cookieGrid[p.row][p.col+1]);
                stack.add(a);
                if (a.total>mostCookiesSoFar)
                {
                    mostCookiesSoFar = a.total;
                }
            }
            if (goodPoint(p.row+1,p.col))
            {
                PathMarker b = new PathMarker(p.row+1,p.col,p.total+cookieGrid[p.row+1][p.col]);
                stack.add(b);
                if (b.total>mostCookiesSoFar)
                {
                    mostCookiesSoFar = b.total;
                }
            }
        }
        return mostCookiesSoFar;

    }

    /* Calculate which route grants the most cookies using a priority queue. */
    /* From any given position, always add the path right before adding the path down */
    public int pqCookies() {  //breath first search
        PriorityQueue<PathMarker> priorityQueue = new PriorityQueue<PathMarker>();
        int mostCookiesSoFar = cookieGrid[0][0];
        priorityQueue.add(new PathMarker(0, 0, cookieGrid[0][0]));
        if (cookieGrid[0][0] == -1)
        {
            return 0;
        }
        while (!priorityQueue.isEmpty())
        {
            PathMarker p = priorityQueue.poll();
            if (goodPoint(p.row,p.col+1))
            {
                PathMarker b = new PathMarker(p.row,p.col+1,p.total+cookieGrid[p.row][p.col+1]);
                priorityQueue.add(b);
                if (b.total>mostCookiesSoFar)
                {
                    mostCookiesSoFar = b.total;
                }
            }
            if (goodPoint(p.row+1,p.col))
            {
                PathMarker a = new PathMarker(p.row+1,p.col,p.total+ cookieGrid[p.row+1][p.col]);
                priorityQueue.add(a);
                if (a.total>mostCookiesSoFar)
                {
                    mostCookiesSoFar = a.total;
                }
            }
        }
        return mostCookiesSoFar;
    }

    //Constructs a CookieMonster from a file with format:
    //numRows numCols
    //<<rest of the grid, with spaces in between the numbers>>
    public CookieMonster(String fileName) {
        int row = 0;
        int col = 0;
        try
        {
            Scanner input = new Scanner(new File(fileName));

            numRows    = input.nextInt();
            numCols    = input.nextInt();
            cookieGrid = new int[numRows][numCols];

            for (row = 0; row < numRows; row++)
                for (col = 0; col < numCols; col++)
                    cookieGrid[row][col] = input.nextInt();

            input.close();
        }
        catch (Exception e)
        {
            System.out.print("Error creating maze: " + e.toString());
            System.out.println("Error occurred at row: " + row + ", col: " + col);
        }

    }

    public static void main (String[] args)
    {   int[][] grid2 = {{4, 0, 1, -1, 0, 3, 2, 2, 4, 1, 4}, {1, 4, 1, 1, 6, 1, 4, 5, 2, 1, 0}, {2, 5, 2, 0, 7, -1, 2, 1, 0, -1, 3}, {-1, 4, -1, -1, 3, 5, 1, 4, 2, 1, 2}, {4, 8, -1, 3, 2, 2, -1, 4, -1, 0, 0}, {1, 0, 4, 1, -1, 8, 0, 2, -1, 2, 5}};
        int[][] grid1 = {{3, 0, 5, -1, 7, -1, -1, 0, 4, 2, 1}, {3, 2, 1, -1, 4, -1, 5, 3, -1, 1, 0, -1, 1, 0}, {4, 8, -1, 3, 2, 2, -1, 4, -1, 0, 0}, {1, 0, 4, 1, -1, 8, 0, 2, -1, 2, 5}};
        int[][] grid = {{3, 0, 5, -1, 7, -1, -1, 0, 4, 2, 1}, {3, 2, 1, -1, 4, -1, 5, 3, -1, 1, 0, -1, 1, 0}, {4, 8, -1, 3, 2, 2, -1, 4, -1, 0, 0}, {1, 0, 4, 1, -1, 8, 0, 2, -1, 2, 5}, {4, 0, 1, -1, 0, 3, 2, 2, 4, 1, 4}, {1, 4, 1, 1, 6, 1, 4, 5, 2, 1, 0}, {2, 5, 2, 0, 7, -1, 2, 1, 0, -1, 3}, {-1, 4, -1, -1, 3, 5, 1, 4, 2, 1, 2}, {4, 8, -1, 3, 2, 2, -1, 4, -1, 0, 0}, {1, 0, 4, 1, -1, 8, 0, 2, -1, 2, 5}};
        int[][] grid9 = {{-1, -1, -1, -1, -1}, {2, 3, -1, -1, -1}, {-1, 5, 7, -1, -1}, {-1, -1, 8, 1, -1}, {-1, -1, -1, 0, 14}};
        int[][] mat = new int[][]{{0,-1},{-1,-1}};
        CookieMonster cookie = new CookieMonster(grid9);
        System.out.println(cookie.recursiveOptimalPath(0,0));
        System.out.println(cookie.stackCookies());
        System.out.println(cookie.queueCookies());
        System.out.println(cookie.pqCookies());
    }

}
