package cinema;

import java.util.*;

public class Cinema {

    public static void main(String[] args) {
    
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        String input = scanner.nextLine();
        int rows = Integer.parseInt(input);
        System.out.println("Enter the number of seats in each row:");
        input = scanner.nextLine();
        int columns = Integer.parseInt(input);

        Action action = new Action(scanner, rows, columns);
        Menu menu = new Menu(scanner);

        int menuNo = -1;
        while (menuNo != 0) {
            menu.display();
            menuNo = menu.getMenuNo();
            switch (menuNo) {
                case 1:
                    action.show();
                    break;
                case 2:
                    action.buy();
                    break;
                case 3:
                    action.statics();
                    break;
                case 0:
                    break;
            }
        }

        scanner.close();
    }
}

class Action {

    Scanner scanner;
    Theatre theatre;
    int rows;
    int columns;
    int currentIncome = 0;
    int totalIncome;
    int numberOfPurchased = 0;
    int totalSeats;

    Action(Scanner scanner, int rows, int columns) {
        this.scanner = scanner;
        this.rows = rows;
        this.columns = columns;
        theatre = new Theatre(rows, columns);
        totalSeats = rows * columns;

        totalIncome = 0;
        int seats = rows * columns;
        if (seats < 60) {
            totalIncome = seats * 10;
        } else {
            int halfOfRows = rows / 2;
            totalIncome = halfOfRows * columns * 10;
            totalIncome += (rows - halfOfRows) * columns * 8;  
        }
    }

    void show() {
        System.out.println();
        theatre.display();
    }

    void buy() {

        boolean reserveOk = false;
        int row = 0;
        while (!reserveOk) {
            System.out.println();
            System.out.println("Enter a row number:");
            String input = scanner.nextLine();
            row = Integer.parseInt(input);
            System.out.println("Enter a seat number in that row:");
            input = scanner.nextLine();
            int column = Integer.parseInt(input);
            reserveOk = theatre.reserve(row, column);
        }

        numberOfPurchased++;

        int price = 0;
        int seats = rows * columns;
        if (seats < 60) {
            price = 10;
        } else {
            int halfOfRows = rows / 2;
            if (row <= halfOfRows) {
                price = 10;
            } else {
                price = 8;
            }
        }
        currentIncome += price;

        System.out.println();
        System.out.println("Ticket price: $" + price); 
    }

    void statics() {
        System.out.println();
        System.out.println("Number of purchased tickets: " + numberOfPurchased);
        System.out.println(String.format("Percentage: %.2f%%", (double)numberOfPurchased * 100.0 / (double)totalSeats));
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }
}

class Menu {

    Scanner scanner;
    Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    void display() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    int getMenuNo() {
        System.out.println();
        String input = scanner.nextLine();
        return Integer.parseInt(input);
    }
}

class Theatre{
    private int rows;
    private int columns;
    private char[][] seats;

    Theatre(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        seats = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0;j < columns; j++) {
                seats[i][j] = 'S';
            }
        }
    }

    int getRows() {
        return rows;
    }

    int getColumns() {
        return columns;
    }

    void setRows(int rows) {
        this.rows = rows;
    }

    void setColumns(int columns) {
        this.columns = columns;
    }

    boolean reserve(int row, int column) {
        if (row > rows || column > columns) {
            System.out.println();
            System.out.println("Wrong input!");
            return false;
        }

        if (seats[row - 1][column - 1] == 'B') {
            System.out.println();
            System.out.println("That ticket has already been purchased!");
            return false;
        }
        seats[row - 1][column - 1] = 'B';
        return true;
    }

    void display() {
        System.out.println("Cinema:");
        StringBuilder sb = new StringBuilder();
        sb.append(' ');
        sb.append(' ');
        for (int j = 0; j < columns; j++) {
            if (j > 0) {
                sb.append(' ');
            }
            sb.append(j + 1);
        }
        System.out.println(sb.toString());
        for (int i = 0; i < rows; i++) {
            sb = new StringBuilder();
            sb.append(i + 1);
            sb.append(' ');
            for (int j = 0; j < columns; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append(seats[i][j]);
            }
            System.out.println(sb.toString());
        }
    }
}