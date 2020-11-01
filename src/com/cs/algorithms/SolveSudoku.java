package com.cs.algorithms;

import java.util.concurrent.TimeUnit;

public class SolveSudoku {
    final public int[][] sudoku;

    SolveSudoku(final int[][] sudoku) {
        this.sudoku = sudoku;
    }

    public static void main(String[] args) {


        SolveSudoku sudoku = new SolveSudoku(
                new int[][]{
                        new int[]{0, 0, 5, 3, 0, 0, 0, 0, 0},
                        new int[]{8, 0, 0, 0, 0, 0, 0, 2, 0},
                        new int[]{0, 7, 0, 0, 1, 0, 5, 0, 0},
                        new int[]{4, 0, 0, 0, 0, 5, 3, 0, 0},
                        new int[]{0, 1, 0, 0, 7, 0, 0, 0, 6},
                        new int[]{0, 0, 3, 2, 0, 0, 0, 8, 0},
                        new int[]{0, 6, 0, 5, 0, 0, 0, 0, 9},
                        new int[]{0, 0, 4, 0, 0, 0, 0, 3, 0},
                        new int[]{0, 0, 0, 0, 0, 9, 7, 0, 0}});
        sudoku.print();
        long timeStart = System.nanoTime();
        sudoku.backtrack(sudoku.hasEmptyCell());

        long timeEnd = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(timeEnd - timeStart) + "ms");
        sudoku.print();
    }

    public void print() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
    }

    private Cell hasEmptyCell() {
        for (int i = 0; i < this.sudoku.length; i++) {
            for (int j = 0; j < this.sudoku[i].length; j++) {
                if (this.sudoku[i][j] == 0) return new Cell(i, j);
            }
        }
        return new Cell(-1, -1);
    }

    private boolean rowContainsValue(int row, int x) {
        for (int i = 0; i < this.sudoku.length; i++) {
            if (this.sudoku[row][i] == x) return true;
        }
        return false;
    }

    private boolean columnContainsValue(int column, int x) {
        for (int i = 0; i < this.sudoku.length; i++) {
            if (this.sudoku[i][column] == x) return true;
        }
        return false;
    }

    private boolean boxContainsValue(int row, int column, int x) {
        int cornerI = row - row % 3;
        int cornerJ = column - column % 3;
        for (int ci = cornerI; ci < cornerI + 3; ci++) {
            for (int cj = cornerJ; cj < cornerJ + 3; cj++) {
                if (this.sudoku[ci][cj] == x) return true;
            }
        }
        return false;
    }

    private boolean backtrack(Cell backtrackCell) {
        for (int z = 1; z <= this.sudoku.length; z++) {
            boolean isValid = true;
            if (this.rowContainsValue(backtrackCell.i, z)) isValid = false;
            if (isValid && this.columnContainsValue(backtrackCell.j, z)) isValid = false;
            if (isValid && this.boxContainsValue(backtrackCell.i, backtrackCell.j, z)) isValid = false;
            if (!isValid) continue;
            this.sudoku[backtrackCell.i][backtrackCell.j] = z;
            final Cell emptyCell = this.hasEmptyCell();
            if (emptyCell.i == -1 && emptyCell.j == -1) return true;
            else if (backtrack(emptyCell)) return true;
        }
        this.sudoku[backtrackCell.i][backtrackCell.j] = 0;
        return false;
    }

    private class Cell {
        public int i;
        public int j;

        public Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
