import java.io.File;
import java.io.Exception;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * This program solves mazes with breadth first search and creates mazes randomly
 * Author: Pranav Kalsi
 * Date Created: May 19th, 2020
 * Last Modified: June 15th, 2020
 * Assumptions: User doesn't input any files.
 */

public class Main {
    //start node
    Integer[] startNode;

    //target node
    Integer[] endNode;

    public static void main(String[] args) throws Exception {
        //call constructor method
        new Main();
    }//end of main method

    public Main() throws Exception {
        while (true) {
            //go to the menu
            menu();
        }//end of while loop
    }//end of constructor method

    /**
     * This method displays the menu screen
     */
    public void menu() throws Exception {
        //Scanner to get input
        Scanner in = new Scanner(System.in);

        //store the the value of input
        char choice = 'e';

        //display the menu header
        System.out.println("---------------------------");
        System.out.println("            Menu");
        System.out.println("---------------------------\n\n");

        //display the options
        System.out.println("Enter 1 to get Instructions\nEnter 2 to go to The Maze Solver\nEnter 3 to Generate a Maze\nEnter 4 to Exit");
        System.out.print("Choice: ");

        //do while loop to get valid input
        do {
            try {
                //get input for what user wants to do
                choice = in.nextLine().charAt(0);
            } catch (Exception e) {
                //catch invalid input
                System.out.println("Invalid entry! You can only enter (1/2/3)");

                //re prompt for input
                System.out.print("Choice: ");

                //skip iteration
                continue;
            }//end of try catch

            //if statement to catch invalid character
            if (choice != '1' && choice != '2' && choice != '3' && choice != '4') {
                System.out.println("Invalid entry! You can only enter (1/2/3)");
                System.out.print("Choice: ");
            }//end of if statement

        } while (choice != '1' && choice != '2' && choice != '3' && choice != '4');//end of do-while loop

        //clear the screen
        clear();

        //go to the appropriate place
        switch (choice) {
            case '1':
                //go to the instructions page
                instructions();

                break;
            case '2':
                //go to the maze solving section of the program
                mazeSolverControl();

                //clear the screen
                clear();

                break;
            case '3':
                //go to maze create control
                mazeCreatorControl();

                //clear the screen
                clear();

                break;
            case '4':
                //exit the program
                exit();

                break;
        }//end of switch statement
    }//end of method menu

    /**
     * This method controls all of the maze creation operations
     */
    public void mazeCreatorControl() throws Exception {
        //display title screen
        mazeCreatorTitle();

        //pause and clear the screen
        pause();
        clear();

        //Scanner for input
        Scanner in = new Scanner(System.in);

        //prompt for amount of rows
        System.out.print("How many rows do you want in the maze: ");
        int rows = in.nextInt();

        //prompt for amount of columns
        System.out.print("How many columns do you want in the maze: ");
        int cols = in.nextInt();

        //pickup trailing whitespace
        in.nextLine();

        //create the maze
        String[][] generatedMaze = new String[rows][cols];

        //generate the maze
        char happy = 'e';//variable to check if the user is happy witht eh create maze

        //do while loop to check if user is happy with generated maze
        do {
            //create maze
            System.out.println("Here is a fresh maze I generated...");
            createMaze(generatedMaze, rows, cols);

            //error handling make sure a valid input is entered
            do {
                //prompt user
                System.out.print("Are you happy with this maze (y/n): ");

                //make sure user enters a character
                try {
                    happy = in.nextLine().toLowerCase().charAt(0);
                } catch (Exception e) {
                    System.out.println("Invalid input! You can only enter y or n!");
                    System.out.println("Try again!");
                }//end of try catch statement

                //make sure they only enter y or n
                if (happy != 'n' && happy != 'y') {
                    System.out.println("Invalid input! You can only enter y or n!");
                    System.out.println("Try again!");
                }//end of if statement

            } while (happy != 'n' && happy != 'y');//end of do while
        } while (happy != 'y');//end of do while

        //Tell user maze is being saved
        System.out.println("This maze is going to be saved for future use!");

        //pause the screen
        pause();

        //save the maze
        saveMaze(generatedMaze);
    }//end of method mazeCreatorControl

    /**
     * This method displays the title screen for the maze creator
     */
    public void mazeCreatorTitle() {
        //display title
        System.out.println("'##::::'##::::'###::::'########:'########:");
        System.out.println(" ###::'###:::'## ##:::..... ##:: ##.....::");
        System.out.println(" ####'####::'##:. ##:::::: ##::: ##:::::::");
        System.out.println(" ## ### ##:'##:::. ##:::: ##:::: ######:::");
        System.out.println(" ##. #: ##: #########::: ##::::: ##...::::");
        System.out.println(" ##:.:: ##: ##.... ##:: ##:::::: ##:::::::");
        System.out.println(" ##:::: ##: ##:::: ##: ########: ########:");
        System.out.println("..:::::..::..:::::..::........::........::");

        System.out.println(" ##                      #                ");
        System.out.println("#  #                     #                ");
        System.out.println("#     ###    ##    ###  ###    ##   ###   ");
        System.out.println("#     #  #  # ##  #  #   #    #  #  #  #  ");
        System.out.println("#  #  #     ##    # ##   #    #  #  #     ");
        System.out.println(" ##   #      ##    # #    ##   ##   #");
    }//end of method mazeCreatorTitle

    /**
     * This method saves the maze passed in, into a text file
     * @param generatedMaze The maze you want to save
     */
    public void saveMaze(String[][] generatedMaze) throws Exception {
        //Setup fileIO
        File file = new File("numberOfMaze.txt");
        Scanner in = new Scanner(file);

        //read how many files there are and add one
        int numOfFiles = in.nextInt() + 1;

        //create the next file name
        String newFile = "MAZE" + (String.valueOf(numOfFiles)) + ".txt";

        //update the number of files in numberOfMaze.txt
        PrintWriter pw = new PrintWriter("numberOfMaze.txt");
        pw.println(numOfFiles);
        pw.close();

        //setup PrintWriter for the new file
        pw = new PrintWriter(newFile);

        //output the dimensions of the maze to the file
        pw.println(generatedMaze.length + " " + generatedMaze[0].length);

        //output the maze to the file
        for (int i = 0; i < generatedMaze.length; i++) {
            for (int j = 0; j < generatedMaze[i].length; j++) {
                pw.print(generatedMaze[i][j]);
            }//end of for loop going through the columns
            pw.println("");
        }//end of for loop going through the columns

        //close the PrintWriter
        pw.close();
    }//end of method save maze

    /**
     * This method generates a random maze
     * @param generatedMaze The 2D array in which the maze will be stored
     * @param rows
     * @param cols
     */
    public void createMaze(String[][] generatedMaze, int rows, int cols) {
        //plug in the starting point to the top left corner
        generatedMaze[0][0] = "S";

        //plug in the starting point to the bottom right corner
        generatedMaze[rows - 1][cols - 1] = "E";

        //go through the maze and print the random values put in
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //skip the top left cell and bottom left cell
                if ((i == 0 && j == 0) || (i == rows - 1 && j == cols - 1)) {
                    System.out.print(generatedMaze[i][j]);
                    continue;
                }//end of if statement

                //pick a random number from 0-2
                int rand = (int) (Math.random() * 3);

                //if the random integer equals to 2 put a wall else a open spot
                generatedMaze[i][j] = (rand == 2 ? "#" : ".");

                //print the node
                System.out.print(generatedMaze[i][j]);
            }//end of for loop

            //go to the next line
            System.out.println("");
        }//end of for loop
    }//end of method createMaze

    /**
     * This method exits the program
     */
    public void exit() throws Exception {
        //display the header
        System.out.println("'########:'##::::'##::::'###::::'##::: ##:'##:::'##::'######::'####:");
        System.out.println("... ##..:: ##:::: ##:::'## ##::: ###:: ##: ##::'##::'##... ##: ####:");
        System.out.println("::: ##:::: ##:::: ##::'##:. ##:: ####: ##: ##:'##::: ##:::..:: ####:");
        System.out.println("::: ##:::: #########:'##:::. ##: ## ## ##: #####::::. ######::: ##::");
        System.out.println("::: ##:::: ##.... ##: #########: ##. ####: ##. ##::::..... ##::..:::");
        System.out.println("::: ##:::: ##:::: ##: ##.... ##: ##:. ###: ##:. ##::'##::: ##:'####:");
        System.out.println("::: ##:::: ##:::: ##: ##:::: ##: ##::. ##: ##::. ##:. ######:: ####:");
        System.out.println(":::..:::::..:::::..::..:::::..::..::::..::..::::..:::......:::....::");

        //Display a message
        System.out.println("Thanks for playing!");

        //pause the screen
        pause();

        //close the input stream
        System.in.close();

        //exit the program
        System.exit(0);
    }//end of method exit

    /**
     * Display a instructions screen
     */
    public void instructions() {
        //display the header
        System.out.println("'####:'##::: ##::'######::'########:'########::'##::::'##::'######::'########:'####::'#######::'##::: ##::'######::");
        System.out.println(". ##:: ###:: ##:'##... ##:... ##..:: ##.... ##: ##:::: ##:'##... ##:... ##..::. ##::'##.... ##: ###:: ##:'##... ##:");
        System.out.println(": ##:: ####: ##: ##:::..::::: ##:::: ##:::: ##: ##:::: ##: ##:::..::::: ##::::: ##:: ##:::: ##: ####: ##: ##:::..::");
        System.out.println(": ##:: ## ## ##:. ######::::: ##:::: ########:: ##:::: ##: ##:::::::::: ##::::: ##:: ##:::: ##: ## ## ##:. ######::");
        System.out.println(": ##:: ##. ####::..... ##:::: ##:::: ##.. ##::: ##:::: ##: ##:::::::::: ##::::: ##:: ##:::: ##: ##. ####::..... ##:");
        System.out.println(": ##:: ##:. ###:'##::: ##:::: ##:::: ##::. ##:: ##:::: ##: ##::: ##:::: ##::::: ##:: ##:::: ##: ##:. ###:'##::: ##:");
        System.out.println("'####: ##::. ##:. ######::::: ##:::: ##:::. ##:. #######::. ######::::: ##::::'####:. #######:: ##::. ##:. ######::");
        System.out.println("....::..::::..:::......::::::..:::::..:::::..:::.......::::......::::::..:::::....:::.......:::..::::..:::......:::");

        //add line spaces
        System.out.println("\n\n\n");

        //The instructions
        System.out.println("Welcome to the maze solver, in this program you can do a few thing!");
        System.out.println("1. Maze Solver - The computer will pick a random maze from the txt files and solve it!");
        System.out.println("The 'X's are the trail from the start to the finish.");
        System.out.println("2. Maze Creator- The computer will generate a maze of the dimensions you enter and save it!");
        System.out.println("*As the user you are not supposed to feed in your own txt files, this program reads the mazes in a special way*");

        //pause and clear the screen
        pause();
        clear();
    }//end of method instructions

    /**
     * This method will pause the program
     */
    public void pause() {
        Scanner in = new Scanner(System.in);
        System.out.println("Press the ENTER key to continue!");
        in.nextLine();
    }//end of method pause

    /**
     * The method will simulate the clearing of the screen by
     * printing a bunch of new line characters
     */
    public void clear() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }//end of method clear

    /**
     * This method controls all of the maze solving operations
     */
    public void mazeSolverControl() throws Exception {
        //Display the title screen
        mazeSolverTitle();

        //pause and clear the screen
        pause();
        clear();

        //pick a maze
        final String mazePath = pickMaze();

        //read the maze in that path
        int[][] maze = readMaze(mazePath);

        //create adjacency list
        HashMap<Integer, Set<Integer[]>> adjacencyList = new HashMap<>();

        //fill the list in the hashmap
        createAdjacencyList(adjacencyList, maze);

        //array to store previously visited nodes
        int[] prev = bfs(adjacencyList, maze);

        //retrace the path if prev does not equal null
        //else print impossible maze
        if (prev != null) {
            String[][] stringMaze = retrace(prev, maze);
            printMaze(stringMaze, maze);
        } else {
            System.out.println("This maze is impossible!");
        }//end of if-else statements

        //pause the program
        pause();

        //clear the screen
        clear();

        //tell user they will be redirected to the menu
        System.out.println("You will be redirected to the menu");

        //pause and clear the screen
        pause();
        clear();
    }//end of method mazeSolverControl

    /**
     * This is the title screen for the maze solver
     */
    public void mazeSolverTitle() {
        System.out.println("'##::::'##::::'###::::'########:'########:::::'######:::'#######::'##:::::::'##::::'##:'########:'########::");
        System.out.println(" ###::'###:::'## ##:::..... ##:: ##.....:::::'##... ##:'##.... ##: ##::::::: ##:::: ##: ##.....:: ##.... ##:");
        System.out.println(" ####'####::'##:. ##:::::: ##::: ##:::::::::: ##:::..:: ##:::: ##: ##::::::: ##:::: ##: ##::::::: ##:::: ##:");
        System.out.println(" ## ### ##:'##:::. ##:::: ##:::: ######::::::. ######:: ##:::: ##: ##::::::: ##:::: ##: ######::: ########::");
        System.out.println(" ##. #: ##: #########::: ##::::: ##...::::::::..... ##: ##:::: ##: ##:::::::. ##:: ##:: ##...:::: ##.. ##:::");
        System.out.println(" ##:.:: ##: ##.... ##:: ##:::::: ##::::::::::'##::: ##: ##:::: ##: ##::::::::. ## ##::: ##::::::: ##::. ##::");
        System.out.println(" ##:::: ##: ##:::: ##: ########: ########::::. ######::. #######:: ########:::. ###:::: ########: ##:::. ##:");
        System.out.println("..:::::..::..:::::..::........::........::::::......::::.......:::........:::::...:::::........::..:::::..::");
    }//end of method mazeSolverTitle

    /**
     * This method retraces the shortest path and stores it in a String array
     *
     * @param prev Array with each nodes previous node
     * @param maze The maze from which the shortest path is being derived
     * @return a String array with the shortest path
     */
    public String[][] retrace(int[] prev, int[][] maze) {
        //String version of maze in which shortest path will be stored
        String[][] mazeCopy = new String[maze.length][maze[0].length];

        //Value of start and end nodes
        int startNodeVal = maze[startNode[0]][startNode[1]] - 1;
        int endNodeVal = maze[endNode[0]][endNode[1]] - 1;

        //Put the value of 'X' in the end node's spot in the
        //String array represent it as apart of the path
        mazeCopy[endNode[0]][endNode[1]] = "X";

        //Create a variable to store the previous nodes
        //and initialize it to the value of the end node
        int previousNode = endNodeVal;

        while (true) {
            //if last node was the start node break the while loop
            if (previousNode == startNodeVal) {
                break;
            }//end of if statement

            //set the current node value to the previous node's parent node
            int nextNode = prev[previousNode] + 1;

            //for loop to go through the maze and find the target node (nextNode)
            for (int i = 0; i < maze.length; i++) {
                boolean bool = false;
                for (int j = 0; j < maze[i].length; j++) {
                    //check if this element is equal to the target node (nextNode)
                    if (maze[i][j] == nextNode) {
                        mazeCopy[i][j] = "X";
                        previousNode = nextNode - 1;
                        bool = true;
                    }//end of if statement
                }//end of for loop going through columns

                //if target node has been found then break the loop
                if (bool) break;
            }//end of for loop going through rows
        }//end of while loop

        //return the String [] [] version of maze
        //containing each node's appropriate symbol
        return mazeCopy;
    }//end of method retrace

    /**
     * Perform a Breadth First Search on the adjacency list representing the maze passed in
     * @param adjacencyList The adjacency list of the maze
     * @param maze the maze on which the search is being performed
     * @return A array storing each node previously visited node or null is maze is impossible
     */
    public int[] bfs(HashMap<Integer, Set<Integer[]>> adjacencyList, int[][] maze) throws Exception {
        //tell user maze is being solved
        System.out.print("Solving");
        for(int i = 0; i < 3; i++){
            Thread.sleep(500);
            System.out.print(".");
        }//end for loop

        //move cursor to next line and add line spaces
        System.out.println("\n\n\n");

        //store visited nodes
        ArrayList<Integer> visited = new ArrayList<>();

        //store node visited before
        int[] prev = new int[maze.length * maze[0].length];

        //The queue of nodes
        ArrayList<Integer[]> queue = new ArrayList<>();

        //add start node to the queue
        queue.add(startNode);

        //store if we got to the end
        boolean success = false;

        //Breadth First Search
        while (queue.size() > 0) {
            Integer[] currentNode = queue.remove(0);
            int nodeVal = maze[currentNode[0]][currentNode[1]];
            visited.add(nodeVal);

            //check if current node is the end node
            if (Arrays.equals(currentNode, endNode)) {
                success = true;
                break;
            }//end of if

            //add the neighbours to the queue
            for (Integer[] neighbour : adjacencyList.get(nodeVal)) {
                int node = maze[neighbour[0]][neighbour[1]];

                //check if node has been visited or not
                if (!(visited.contains(node))) {
                    queue.add(neighbour);
                    visited.add(node);
                    prev[node - 1] = nodeVal - 1;
                }//end of if to add neighbours
            }//end if for loop to go through neighbours
        }//end of while loop

        //return null is maze is not possible
        if (!success) {
            return null;
        }//end of if statement

        //return array with all the previously visited nodes if end node is reached
        return prev;
    }//end of method bfs

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
     * @return File path to one of the txt files
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
        System.out.println("I selected this maze to solve!");

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
                    startNode = new Integer[]{i, j};
                    nodeNumber++;
                } else if (c == 'E') {
                    maze[i][j] = nodeNumber;
                    endNode = new Integer[]{i, j};
                    nodeNumber++;
                }//end of if - else structure

                //increment j
                j++;
            }//end of for loop going through each node in row

            //move cursor to next line
            System.out.println();
        }//end of for loop going through each row

        //pause the screen
        pause();

        //Add 3 line spaces
        System.out.println("\n\n");

        //return the maze
        return maze;
    }//end of method readMaze

    /**
     * This method prints a maze that is passed through
     *
     * @param maze    The maze to be printed
     * @param intMaze The maze with integer nodes
     */
    public void printMaze(String[][] maze, int[][] intMaze) {
        //Tell user this is the solution
        System.out.println("The solution to this maze is:");

        //print the maze
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                //Print appropriate symbol for each node
                if (Arrays.equals(new Integer[]{i, j}, startNode)) {
                    System.out.print("S ");
                    continue;
                } else if (Arrays.equals(new Integer[]{i, j}, endNode)) {
                    System.out.print("E ");
                    continue;
                }//end of first if - else
                try {
                    if (maze[i][j].equals("X")) {
                        System.out.print("X ");
                        continue;
                    }
                } catch (Exception e) {
                }

                //if node = -1 print # or else print .
                System.out.print(intMaze[i][j] == -1 ? "# " : ". ");
            }//end of for loop going through columns

            //move cursor to next line
            System.out.println();
        }//end of for loop going through the columns
    }//end of method printMaze
}//end of class Main
