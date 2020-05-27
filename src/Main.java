import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/*
* THINGS TO DO
* MAKE THE MENU NICER
* MAKE THE INSTRUCTIONS
* MAKE THE EXIT
* MAKE THE TITLE SCREEN FOR THE MAZE SOLVER
* ASK SHARLEEN IF I HAVE TO TELL USER THE ERROR THEY MADE
* CREATE MAZE GENERATOR
* */

public class Main {
    //start node
    Integer [] startNode;

    //target node
    Integer [] endNode;

    public static void main(String[] args) throws Exception {
        //call constructor method
        new Main();
    }//end of main method

    /**
     * The constructor method
     */
    public Main() throws Exception {
        mazeSolverControl();
        while (true) {
            //menu
            break;
        }//end of while loop
    }//end of constructor method

    //--------------------------------menu------------------------------------------
    public void menu() throws Exception {
        Scanner in = new Scanner(System.in);
        char choice = 'm';

        System.out.println("---------------------------");
        System.out.println("            Menu");
        System.out.println("---------------------------\n\n");

        System.out.println("Enter 1 to get instructions\nEnter 2 to go to the maze solver\nEnter 3 to exit");
        System.out.print("Choice: ");

        do {
            try {
                choice = in.nextLine().charAt(0);
            } catch (Exception e) {
                System.out.println("Invalid entry! You can only enter (1/2/3)");
                System.out.println("You will be redirected to the menu...");
                pause();
                clear();
                menu();
            }//end of try catch

            if(choice != '1' && choice != '2' && choice != '3'){
                System.out.println("Invalid entry! You can only enter (1/2/3)");
                System.out.print("Choice: ");
            }//end of if statement

        }while(choice != '1' && choice != '2' && choice != '3');//end of do-while loop

        //clear the screen
        clear();

        //go to the appropriate place
        switch(choice){
            case '1':
                instructions();

                pause();

                clear();

                menu();

                break;

            case '2':
                mazeSolverControl();

                clear();
                break;

            case '3':
                exit();
                break;
        }
    }

    public void exit() {
    }

    public void instructions() {
    }

    /**
     * This method will pause the program
     */
    public void pause(){
        Scanner in = new Scanner(System.in);
        System.out.println("Press the ENTER key to continue!");
        in.nextLine();
    }//end of method pause

    /**
     * The method will simulate the clearing of the screen by
     * printing a bunch of new line characters
     */
    public void clear(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }//end of method clear

    /**
     * This method controls all of the maze solving operations
     */
    public void mazeSolverControl() throws Exception {
        //pick a maze
        final String mazePath = pickMaze();

        //read the maze in that path
        int[][] maze = readMaze(mazePath);

        //create adjacency list
        HashMap<Integer, Set<Integer[]>> adjacencyList = new HashMap<>();

        //fill the list in the hashmap
        createAdjacencyList(adjacencyList, maze);

        //array to store previously visited nodes
        int [] prev = bfs(adjacencyList, maze);

        //retrace the path if prev does not equal null
        //else print impossible maze
        if(prev != null) {
            String[][] stringMaze = retrace(prev, maze);
            printMaze(stringMaze, maze);
        }else {
            System.out.println("This maze is impossible!");
        }//end of if-else statements
    }//end of method mazeSolverControl

    /**
     * This method retraces the shortest path and stores it in a String array
     * @param prev Array with each nodes previous node
     * @param maze The maze from which the shortest path is being derived
     * @return a String array with the shortest path
     */
    public String [] [] retrace(int[] prev, int [] [] maze) {
        //String version of maze in which shortest path will be stored
        String [] [] mazeCopy = new String[maze.length][maze[0].length];

        //Value of start and end nodes
        int startNodeVal = maze[startNode[0]][startNode[1]] - 1;
        int endNodeVal = maze[endNode[0]][endNode[1]] - 1;

        //Put the value of 'X' in the end node's spot in the
        //String array represent it as apart of the path
        mazeCopy[endNode[0]][endNode[1]] = "X";

        //Create a variable to store the previous nodes
        //and initialize it to the value of the end node
        int previousNode = endNodeVal;

        while(true){
            //if last node was the start node break the while loop
            if(previousNode == startNodeVal){
                break;
            }//end of if statement

            //set the current node value to the previous node's parent node
            int nextNode = prev[previousNode] + 1;

            //for loop to go through the maze and find the target node (nextNode)
            for(int i = 0; i < maze.length; i++){
                boolean bool = false;
                for(int j = 0; j < maze[i].length; j++){
                    //check if this element is equal to the target node (nextNode)
                    if(maze[i][j] == nextNode){
                        mazeCopy[i][j] = "X";
                        previousNode = nextNode-1;
                        bool = true;
                    }//end of if statement
                }//end of for loop going through columns

                //if target node has been found then break the loop
                if(bool)break;
            }//end of for loop going through rows
        }//end of while loop

        //return the String [] [] version of maze
        //containing each node's appropriate symbol
        return mazeCopy;
    }//end of method retrace

    /**
     * Perform and visualize a Breadth First Search on the adjacency list representing the maze passed in
     * @param adjacencyList The adjecency list of the maze
     * @param maze the maze on which the search is being performed
     * @return A array storing each node previously visited node or null is maze is impossible
     */
    public int[] bfs(HashMap<Integer, Set<Integer[]>> adjacencyList, int [] [] maze) throws Exception {
        //store visited nodes
        ArrayList<Integer>visited = new ArrayList<>();

        //store node visited before
        int [] prev = new int [maze.length*maze[0].length];

        //The queue of nodes
        ArrayList <Integer[]> queue = new ArrayList<>();

        //add start node to the queue
        queue.add(startNode);

        //store if we got to the end
        boolean success = false;

        //Breadth First Search
        while(queue.size() > 0){
            Integer [] currentNode = queue.remove(0);
            int nodeVal = maze[currentNode[0]][currentNode[1]];
            visited.add(nodeVal);

            //check if current node is the end node
            if(Arrays.equals(currentNode, endNode)){
                success = true;
                break;
            }//end of if

            //add the neighbours to the queue
            for(Integer [] neighbour : adjacencyList.get(nodeVal)){
                int node = maze[neighbour[0]][neighbour[1]];

                //check if node has been visited or not
                if(!(visited.contains(node))) {
                    queue.add(neighbour);
                    visited.add(node);
                    prev[node-1] = nodeVal-1;
                }//end of if to add neighbours
            }//end if for loop to go through neighbours
        }//end of while loop

        //return null is maze is not possible
        if(!success) {
            return null;
        }//end of if statement

        //return array with all the previously visited nodes if end node is reached
        return prev;
    }//end of method bfs

    //DELETE AFTER COMPLETE
    public void printList(HashMap<Integer, Set<Integer[]>> adjacencyList){
        for(Integer key : adjacencyList.keySet()){
            System.out.print("Key: " + key + " Value ");
            for(Integer [] arr : adjacencyList.get(key)){
                System.out.print(Arrays.toString(arr));
            }
            System.out.println();
        }
    }

    /**
     * This method stores a adjacency list in a HashMap that is passed in based on the passed in grid
     * @param adjacencyList The HashMap in which the adjacency list is to be stored
     * @param maze The maze on which the adjacency list should be based upon
     */
    public void createAdjacencyList(HashMap<Integer, Set<Integer[]>> adjacencyList, int[][] maze) {
        //All possible moves from any spot
        int[][] moves = {
                {-1, 0},//up
                {1, 0},//down
                {0, 1},//right
                {0, -1}//left
        };

        //Loop through each spot to check for neighbours
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                //check if spot is a wall
                if (maze[i][j] == -1) {
                    continue;
                }//end of if checking if spot is a wall

                //set to store neighbours
                Set<Integer[]> neighbours = new HashSet<>();

                //put neighbours in Set neighbours
                for (int[] move : moves) {
                    //a prospective neighbour
                    int nRow = i + move[0], nCol = j + move[1];

                    if ((nRow >= 0 && nRow < maze.length) && (nCol >= 0 && nCol < maze[i].length)) {
                        if (maze[nRow][nCol] != -1) {
                            neighbours.add(new Integer[]{nRow, nCol});
                        }//end of if checking if spot is not a wall
                    }//end of if statement checking if spot is in bounds
                }//end of for loop recording neighbours

                //Add to adjacency list
                adjacencyList.put(maze[i][j], neighbours);

            }//end of for loop going through maze columns
        }//end of for loop going through the maze rows
    }//end of method createAdjacencyList

    /**
     * This method will return a random file path to one of the txt files that contain mazes
     *
     * @return File path to one of the txt files
     * @throws Exception
     */
    public String pickMaze() throws Exception {
        //setup file reader
        File file = new File("numberOfMaze.txt");
        Scanner reader = new Scanner(file);

        //Read the number in the file representing how many maze files there are
        int numOfMazes = reader.nextInt();

        //pick a random maze
        int number = (int) (Math.random() * numOfMazes) + 1;

        //return the random maze's file path
        return String.format("MAZE" + number + ".txt");
    }//end of method pickMaze

    /**
     * This method reads the maze on the specified file path and prints it as it reads
     * @param filePath the path to the file in which the maze is
     * @return a 2D integer array containing the maze
     * @throws Exception
     */
    public int[][] readMaze(String filePath) throws Exception {
        //setup file reader
        File file = new File(filePath);
        Scanner reader = new Scanner(file);

        //Get rows and columns
        int rows = reader.nextInt(), cols = reader.nextInt();

        //pick up trailing whitespace
        reader.nextLine();

        //store the maze
        int[][] maze = new int[rows][cols];

        //record node number
        int nodeNumber = 1;

        //Tell user this is the maze being solved
        System.out.println("We are solving the following maze");

        //read file and create maze
        for (int i = 0; i < rows; i++) {
            //read line
            String line = reader.nextLine();

            //column number
            int j = 0;

            //go through each character of String line
            for (char c : line.toCharArray()) {
                //print each node of the maze
                System.out.print(c + " ");

                //encode each sign to an int
                if (c == '.') {
                    maze[i][j] = nodeNumber;
                    nodeNumber++;
                } else if (c == '#') {
                    maze[i][j] = -1;
                } else if (c == 'S') {
                    maze[i][j] = nodeNumber;
                    startNode = new Integer[]{i,j};
                    nodeNumber++;
                } else if (c == 'E') {
                    maze[i][j] = nodeNumber;
                    endNode = new Integer[]{i,j};
                    nodeNumber++;
                }//end of if - else structure

                //increment j
                j++;
            }//end of for loop going through each node in row

            //move cursor to next line
            System.out.println();
        }//end of for loop going through each row

        //Add 3 line spaces
        System.out.println("\n\n");

        //return the maze
        return maze;
    }//end of method readMaze

    /**
     * This method prints a maze that is passed through
     * @param maze The maze to be printed
     * @param intMaze The maze with integer nodes
     */
    public void printMaze(String[][] maze, int [] [] intMaze) {
        //Tell user this is the solution
        System.out.println("The solution to this maze is:");
        //print the maze
        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[i].length; j++){
                //Print appropriate symbol for each node
                if (Arrays.equals(new Integer[]{i,j}, startNode)){
                    System.out.print("S ");
                    continue;
                }else if(Arrays.equals(new Integer[]{i,j}, endNode)){
                    System.out.print("E ");
                    continue;
                }//end of first if - else
                try {
                    if (maze[i][j].equals("X")) {
                        System.out.print("X ");
                        continue;
                    }
                }catch (Exception e){}
                if(intMaze[i][j] == -1){
                    System.out.print("# ");
                }else{
                    System.out.print(". ");
                }//end of if else structure
            }//end of for loop going through columns

            //move cursor to next line
            System.out.println();
        }//end of for loop going through the columns
    }//end of method printMaze
}//end of class Main
