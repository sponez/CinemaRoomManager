package cinema;

import java.util.Scanner;

public class Cinema {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static int MAX_SEATS_FOR_SMALL_ROOM = 60;
    private final static int PRICE_FOR_FRONT_SEATS = 10;
    private final static int PRICE_FOR_BACK_SEATS = 8;
    private final static String WRONG_INPUT_MESSAGE = "Wrong input!";

    private static int rows;
    private static int columns;
    private static boolean[][] seats;
    private static int ticketsBoughtAmount = 0;
    private static int income = 0;

    private static void showCinema() {
        System.out.println("Cinema:");

        //First line
        System.out.print(' ');
        for (int j = 1; j <= columns; ++j) {
            System.out.printf(" %d", j);
        }
        System.out.println();

        //Nex lines
        for (int i = 1; i <= rows; ++i) {
            System.out.print(i);
            for (int j = 1; j <= columns; ++j) {
                System.out.print(' ');
                System.out.print(seats[i - 1][j - 1] ? 'B' : 'S');
            }
            System.out.println();
        }
    }

    private static int getTicketPrice(int row) {
        return rows * columns <= MAX_SEATS_FOR_SMALL_ROOM || row <= rows / 2 ?
            PRICE_FOR_FRONT_SEATS :
            PRICE_FOR_BACK_SEATS;
    }

    private static void buyTicket() {
        for (;;) {
            String rowString, columnString;
            int row, column;

            System.out.println("Enter a row number:");
            rowString = SCANNER.next();

            System.out.println("Enter a seat number in that row:");
            columnString = SCANNER.next();

            try {
                row = Integer.parseInt(rowString);
                column = Integer.parseInt(columnString);
            } catch (NumberFormatException numberFormatException) {
                System.out.println(WRONG_INPUT_MESSAGE);
                continue;
            }

            if (row < 1 || row > rows || column < 1 || column > columns) {
                System.out.println(WRONG_INPUT_MESSAGE);
                continue;
            }

            if (!seats[row - 1][column - 1]) {
                int price = getTicketPrice(row);

                seats[row - 1][column - 1] = true;

                System.out.printf(
                    "Ticket price: $%d%n",
                    price
                );

                ++ticketsBoughtAmount;
                income += price;

                break;
            } else {
                System.out.println("That ticket has already been purchased!");
            }
        }
    }

    private static double getPercentageBoughtSeats() {
        double _100Percentage = 100;
        return (double)ticketsBoughtAmount * _100Percentage / (double)(rows * columns);
    }

    private static int getTotalIncome() {
        int totalIncome = 0;

        for (int i = 1; i <= rows; ++i) {
            totalIncome += getTicketPrice(i) * columns;
        }

        return totalIncome;
    }

    private static void statistics() {
        System.out.printf(
            """
            Number of purchased tickets: %d
            Percentage: %.2f%%
            Current income: $%d
            Total income: $%d
            """,
            ticketsBoughtAmount,
            getPercentageBoughtSeats(),
            income,
            getTotalIncome()
        );
    }

    private static int menu() {
        System.out.print(
            """
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
            """
        );
        return SCANNER.nextInt();
    }

    private static void initCinema() {
        for (;;) {
            String rowsString, columnsString;

            System.out.println("Enter the number of rows:");
            rowsString = SCANNER.next();

            System.out.println("Enter the number of seats in each row:");
            columnsString = SCANNER.next();

            try {
                rows = Integer.parseInt(rowsString);
                columns = Integer.parseInt(columnsString);
            } catch (NumberFormatException numberFormatException) {
                System.out.println(WRONG_INPUT_MESSAGE);
                continue;
            }

            if (rows <= 0 || columns <= 0) {
                System.out.println(WRONG_INPUT_MESSAGE);
                continue;
            }

            break;
        }
    }

    public static void main(String[] args) {
        initCinema();

        seats = new boolean[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                seats[i][j] = false;
            }
        }

        for (int option = menu(); option != 0; option = menu()) {
            switch (option) {
                case 1:
                    showCinema();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    statistics();
                default:
                    break;
            }
        }

        SCANNER.close();
    }
}