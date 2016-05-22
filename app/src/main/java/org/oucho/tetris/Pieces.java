package org.oucho.tetris;

import android.util.Log;
import java.util.Random;


class Pieces {

	//The type of the piece, the shape.
	public final byte type;
	
	//The color of the piece. It depends on the type
	private final byte color;
	
	//Array of boxes occupied by the piece
	public final boolean[][] box;
	
	//Array of the boxes in board occupied in the game board
	private final boolean[][] board;
	private boolean[][] aux;
	
	//Rotation state of the piece: 0->1->2->3->0
	private byte rotation;
	
	//Color getter
	public byte getColor(){
		return color;
	}




	public Pieces() {
		Random generator = new Random();

		byte aleatoire = 0;

		byte a = (byte) generator.nextInt(7);

		for (int i = a; i > 0; i-- ) {
			aleatoire = (byte) generator.nextInt(7);
		}
		//sound = MediaPlayer.create(context, R.raw.down);
		type = aleatoire;

		//type = 2;//TESTING, change to be all the same piece

		color = (byte) (type + 1);

		rotation = 0;

		box = new boolean[20][10];

		board = new boolean[20][10];

		for (int i = 0; i < 20; i++)
        	for (int j = 0; j < 10; j++)
        		box[i][j] = false;

	}
	
	/*************************************************/
	/* Puts the piece on top of the board ************/
	/*************************************************/
	public void start(){
		switch (type){
			//In the Values class there is a "graphical description" of each piece

			/// 0 = haut, 1 = bas

			case Values.PIECE_0: //The bar _
				box[0][3] = true;
				box[0][4] = true;
				box[0][5] = true;
				box[0][6] = true;

				break;

			case Values.PIECE_1: //The 'L'-shaped  __|
				box[0][4] = true;
				box[1][4] = true;
				box[1][5] = true;
				box[1][6] = true;


				break;

			case Values.PIECE_2: //The  inverted 'L'-shaped |__  *
				box[0][5] = true;
				box[1][3] = true;
				box[1][4] = true;
				box[1][5] = true;

				break;

			case Values.PIECE_3: //The cube ==
				box[0][4] = true;
				box[0][5] = true;
				box[1][4] = true;
				box[1][5] = true;
				break;

			case Values.PIECE_4: //The inverted 'Z'-shaped S
				box[0][4] = true;
				box[0][5] = true;
				box[1][3] = true;
				box[1][4] = true;
				break;

			case Values.PIECE_5: //The 'Z'-shaped Z
				box[0][4] = true;
				box[0][5] = true;
				box[1][5] = true;
				box[1][6] = true;
				break;

			case Values.PIECE_6: //The inverted 'T'  _|_
				box[0][4] = true;
				box[1][3] = true;
				box[1][4] = true;
				box[1][5] = true;
				break;
		}
	}
	


	public void readBoard(Box a[][]){
		for (int i = 0; i < 20; i++)
        	for (int j = 0; j < 10; j++)
				board[i][j] = a[i][j].getColor() != Values.COLOR_NONE;
	}


	//Called when a piece is going to be moved. Also checks if the piece can be moved. Returns true (and moves the piece) if it's possible
	public boolean moveDown() {


		aux = new boolean[20][10];				//New array. In the end, if everything is OK, it will be copied to the piece array

		for (int j = 0; j < 10; j++)			
			if (box[19][j])				//If any box conforming the piece is in the 19th row...
				return false;					//... don't move

		for (int i = 0; i < 19; i++)			//Examine all the boxes, one by one
        	for (int j = 0; j < 10; j++){

        		if (box[i][j]) { //If the piece is occupying a box

        			if (board[i + 1][j]) //If the box below is already occupied...
						return false;            //... don't move

        			aux[i + 1][j] = true;		//If the box below is free, mark as true in the auxiliary array

        		} else {
					aux[i + 1][j] = false;        //If the box is not occupied by the piece, set to false in the auxiliary array
				}
        	}

		for (int i = 0; i < 20; i++)			//Copy the auxiliary array to the piece array
			System.arraycopy(aux[i], 0, box[i], 0, 10);



		return true;							//Pieces moved!
	}


	
	//Called when a piece is going to be moved. Also checks if the piece can be moved. Returns true (and moves the piece) if it's possible 
	public boolean moveLeft(){
		aux = new boolean[20][10];				//New array. In the end, if everything is OK, it will be copied to the piece array

		for (int i = 0; i < 20; i++)			//Examine all the boxes, one by one
        	for (int j = 0; j < 10; j++) {
        		if (box[i][j]){			//If the piece is occupying a box
        			if (j == 0)					//If this box is behind the left wall...
        				return false;			//... don't move
        			if (board[i][j - 1])//If the box in the left is already occupied...
        				return false;			//... don't move
        			aux[i][j - 1] = true;		//If the box in the left is free and it's not a wall, mark as true in the auxiliary array
        		} else
        			aux[i][j] = false;			//If the box is not occupied by the piece, set to false in the auxiliary array 
        	}

		for (int i = 0; i < 20; i++)			//Copy the auxiliary array to the piece array
			System.arraycopy(aux[i], 0, box[i], 0, 10);
		return true;//Pieces moved!
	}
	
	//Called when a piece is going to be moved. Also checks if the piece can be moved. Returns true (and moves the piece) if it's possible
	public boolean moveRight(){
		aux = new boolean[20][10];				//New array. In the end, if everything is OK, it will be copied to the piece array
		for (int i = 19; i >= 0; i--)			//Examine all the boxes, one by one
        	for (int j = 9; j >= 0; j--){		
        		if (box[i][j]){			//If the piece is occupying a box
        			if (j == 9)					//If this box is behind the right wall...
        				return false;			//... don't move
        			if (board[i][j + 1])//If the box in the right is already occupied...
        				return false;			//... don't move
        			aux[i][j + 1] = true;		//If the box in the left is free and it's not a wall, mark as true in the auxiliary array
        		}
        		else
        			aux[i][j] = false;			//If the box is not occupied by the piece, set to false in the auxiliary array 
        	}
		for (int i = 0; i < 20; i++)			//Copy the auxiliary array to the piece array
			System.arraycopy(aux[i], 0, box[i], 0, 10);
		return true;							//Pieces moved!
	}



	//Called when a piece is going to be rotated. Also checks if the piece can be moved. Returns true (and moves the piece) if it's possible


	public boolean rotateRight(){
		int i = 0;
		int j = 0;
		switch (type){

			// bare
			case Values.PIECE_0:
				//Switch next rotation state
				switch (rotation + 1){

					case 4:
					case 2: //From horizontal to vertical
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//If is behind the left wall, try to move right twice, on no one
						if (j == 0){
							if (!moveRight())
								return false;
							if (!moveRight()){
								moveLeft();
								return false;
							}
							j = j + 2;
						}
						//If is 1 distance the left wall, try to move right twice, on no one
						if (j == 1){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//If is behind the right wall, try to move left
						if (j == 9){
							if (!moveLeft())
								return false;
							j = j - 1;
						}
						//Check availability
						if (board[i + 1][j - 2])
							return false;
						if (board[i + 1][j - 1])
							return false;
						if (board[i + 1][j + 1])
							return false;
						//Perform transformation
						box[i + 1][j - 2] = true;
						box[i + 1][j - 1] = true;
						box[i + 1][j + 1] = true;
						box[i][j] = false;
						box[i + 2][j] = false;
						box[i + 3][j] = false;
						break;

					case 3:
					case 1: //From  vertical to horizontal
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (i == 19)
							return false;
						if (i == 0)
							return false;
						if (board[i - 1][j + 2])
							return false;
						if (board[i + 1][j + 2])
							return false;
						if (board[i + 2][j + 2])
							return false;
						//Perform transformation
						box[i - 1][j + 2] = true;
						box[i + 1][j + 2] = true;
						box[i + 2][j + 2] = true;
						box[i][j] = false;
						box[i][j + 1] = false;
						box[i][j + 3] = false;
						break;

				}
				break;

            // L inversé
			case Values.PIECE_1:
				//Switch new rotation state
				switch (rotation + 1){
					case 1: //From horizontal left-side-up to vertical top-side-right
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (i == 19)
							return false;
						if (board[i][j + 1])
							return false;
						if (board[i][j + 2])
							return false;
						if (board[i + 2][j + 1])
							return false;
						//Perform transformation
						box[i][j + 1] = true;
						box[i][j + 2] = true;
						box[i + 2][j + 1] = true;
						box[i][j] = false;
						box[i + 1][j] = false;
						box[i + 1][j + 2] = false;
						break;

					case 2: //From vertical top-side-right to horizontal right-side-down
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//If is behind the left wall, try to move right
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j - 1])
							return false;
						if (board[i + 1][j + 1])
							return false;
						//Perform transformation
						box[i][j - 1] = true;
						box[i + 1][j + 1] = true;
						box[i + 1][j] = false;
						box[i + 2][j] = false;
						break;

					case 3: //From horizontal left-side-down to vertical down-side-left
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (i == 19)
							return false;
						if (board[i + 2][j + 1])
							return false;
						if (board[i + 2][j + 2])
							return false;
						//Perform transformation
						box[i + 2][j + 1] = true;
						box[i + 2][j + 2] = true;
						box[i][j] = false;
						box[i][j + 1] = false;
						break;

					case 4: //From vertical down-side-left to horizontal left-side-up
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//If is behind the left wall, try to move right
						if (j == 1){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j - 2])
							return false;
						if (board[i + 1][j - 2])
							return false;
						if (board[i + 1][j - 1])
							return false;
						//Perform transformation
						box[i][j - 2] = true;
						box[i + 1][j - 2] = true;
						box[i + 1][j - 1] = true;
						box[i][j] = false;
						box[i + 2][j - 1] = false;
						box[i + 2][j] = false;
						break;
				}
				break;

            // L
			case Values.PIECE_2:
				//Switch new rotation state
				switch (rotation + 1){
					case 1: //From horizontal right-side-up to vertical low-side-right
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (i == 19)
							return false;
						if (board[i][j - 1])
							return false;
						if (board[i + 2][j - 1])
							return false;
						if (board[i + 2][j])
							return false;
						//Perform transformation
						box[i][j - 1] = true;
						box[i + 2][j - 1] = true;
						box[i + 2][j] = true;
						box[i][j] = false;
						box[i + 1][j - 2] = false;
						box[i + 1][j] = false;
						break;

					case 2: //From vertical bottom-side-right to horizontal left-side-down
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move lright
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j - 1])
							return false;
						if (board[i][j + 1])
							return false;
						if (board[i + 1][j - 1])
							return false;						
						//Perform transformation
						box[i][j - 1] = true;
						box[i][j + 1] = true;
						box[i + 1][j - 1] = true;
						box[i + 1][j] = false;
						box[i + 2][j] = false;
						box[i + 2][j + 1] = false;
						break;

					case 3: //From horizontal right-side-down to vertical top-side-left
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}

						//Check availability
						if (i == 18)
                            return false;


                        if (i == 19)
							return false;

                        // rustine:  java.lang.ArrayIndexOutOfBoundsException: length=20; index=20
                        try {
                            if (board[i + i][j + 2])
                                return false;
                        } catch (Exception ignored) {}

                        Log.d("\\_o< coin", "4");


                        if (board[i + 2][j + 2])
							return false;
                        Log.d("\\_o< coin", "5");

						//Perform transformation
						box[i + 1][j + 2] = true;
						box[i + 2][j + 2] = true;
						box[i][j] = false;
						box[i + 1][j] = false;

                        Log.d("\\_o< coin", "6");

                        break;

					case 4: //From vertical top-side-left to horizontal right-side-up
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move right
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i + 1][j - 1])
							return false;
						if (board[i + 1][j])
							return false;
						//Perform transformation
						box[i + 1][j - 1] = true;
						box[i + 1][j] = true;
						box[i][j] = false;
						box[i + 2][j + 1] = false;
						break;
				}
				break;

			case Values.PIECE_3:
				//Is the cube, so, do nothing!
				break;

			case Values.PIECE_4: // inverted 'Z'- shaped
				//Switch new rotation state
				switch (rotation + 1){

					case 3:
					case 1: //From upleft & downright to leftup & rghtdown
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (board[i + 1][j + 1])
							return false;
						if (board[i + 2][j + 1])
							return false;
						//Perform transformation
						box[i + 1][j + 1] = true;
						box[i + 2][j + 1] = true;
						box[i][j + 1] = false;
						box[i + 1][j - 1] = false;
						break;

					case 4:
					case 2: //From leftup & rightdown to upleft & downright 
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move right
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j + 1])
							return false;
						if (board[1 + 1][j - 1])
							return false;				
						//Perform transformation
						box[i][j + 1] = true;
						box[i + 1][j - 1] = true;
						box[i + 1][j + 1] = false;
						box[i + 2][j + 1] = false;
						break;

				}
				break;

			case Values.PIECE_5: // 'Z'- shaped
				//Switch new rotation state
				switch (rotation + 1){

					case 3:
					case 1: //From downleft & upright to rightup & leftdown
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (board[i + 2][j + 1])
							return false;
						if (board[i][j + 2])
							return false;
						//Perform transformation
						box[i + 2][j + 1] = true;
						box[i][j + 2] = true;
						box[i][j] = false;
						box[i][j + 1] = false;
						break;

					case 4:
					case 2: //From rightup & leftdown to downleft & upright
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move right
						if (j == 1){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j - 2])
							return false;
						if (board[1][j - 1])
							return false;				
						//Perform transformation
						box[i][j - 2] = true;
						box[i][j - 1] = true;
						box[i][j] = false;
						box[i + 2][j - 1] = false;
						break;


				}
				break;

			case Values.PIECE_6: // 'T'- shaped
				//Switch new rotation state
				switch (rotation + 1){
					case 1: //From top faced to right faced
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (board[i + 2][j])
							return false;
						//Perform transformation
						box[i + 2][j] = true;
						box[i + 1][j - 1] = false;
						break;
					case 2: //From right faced to bottom faced
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move right
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i + 1][j - 1])
							return false;
						//Perform transformation
						box[i + 1][j - 1] = true;
						box[i][j] = false;
						break;
					case 3: //From bottom faced to left faced
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 0)
							return false;
						if (board[i - 1][j + 1])
							return false;
						//Perform transformation
						box[i - 1][j + 1] = true;
						box[i][j + 2] = false;
						break;
					case 4: //From left faced to top faced
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move left
						if (j == 9){
							if (!moveLeft())
								return false;
							j = j - 1;
						}
						//Check availability
						if (board[i + 1][j + 1])
							return false;
						//Perform transformation
						box[i + 1][j + 1] = true;
						box[i + 2][j] = false;
						break;
				}
				break;
		}
		//Change rotation state
		rotation++;
		if (rotation == 4)
			rotation = 0;
		return true;
	}
	
	public boolean rotateLeft(){
		int i = 0;
		int j = 0;
		switch (type){

			// bare
			case Values.PIECE_0:

				//Switch next rotation state
				switch (rotation - 1){


					// Vertical vers horizontal
					case -1:
					case 1:
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (i == 19)
							return false;
						if (i == 0)
							return false;
						if (board[i - 1][j + 2])
							return false;
						if (board[i + 1][j + 2])
							return false;
						if (board[i + 2][j + 2])
							return false;
						//Perform transformation
						box[i - 1][j + 2] = true;
						box[i + 1][j + 2] = true;
						box[i + 2][j + 2] = true;
						box[i][j] = false;
						box[i][j + 1] = false;
						box[i][j + 3] = false;
						break;


					// Horizontal vers vertical
					case 0:
					case 2:

						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}

						//If is behind the left wall, try to move right twice, on no one
						if (j == 0){
							if (!moveRight())
								return false;
							if (!moveRight()){
								moveLeft();
								return false;
							}
							j = j + 2;
						}

						//If is 1 distance the left wall, try to move right twice, on no one
						if (j == 1){
							if (!moveRight())
								return false;
							j = j + 1;
						}

						//If is behind the right wall, try to move left
						if (j == 9){
							if (!moveLeft())
								return false;
							j = j - 1;
						}

						//Check availability
						if (board[i + 1][j - 2])
							return false;
						if (board[i + 1][j - 1])
							return false;
						if (board[i + 1][j + 1])
							return false;

						//Perform transformation
						box[i + 1][j - 2] = true;
						box[i + 1][j - 1] = true;
						box[i + 1][j + 1] = true;
						box[i][j] = false;
						box[i + 2][j] = false;
						box[i + 3][j] = false;
						break;

				}
				break;

			// L
			case Values.PIECE_1:
				//Switch new rotation state
				switch (rotation - 1){
					case -1: //Horizontal queue vers le haut (left-side-up) - vertical queue vers le gauche low-side-left
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (i == 19)
							return false;
						if (board[i][j + 2])
							return false;
						if (board[i + 2][j + 1])
							return false;
						if (board[i + 2][j + 2])
							return false;
						//Perform transformation
						box[i][j + 2] = true;
						box[i + 2][j + 1] = true;
						box[i + 2][j + 2] = true;
						box[i][j] = false;
						box[i + 1][j] = false;
						box[i + 1][j + 1] = false;
						break;

					case 0: //From vertical top-side-right to horizontal left-side-up
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//If is behind the left wall, try to move right
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j - 1])
							return false;
						if (board[i + 1][j - 1])
							return false;
						if (board[i + 1][j + 1])
							return false;
						//Perform transformation
						box[i][j - 1] = true;
						box[i + 1][j - 1] = true;
						box[i + 1][j + 1] = true;
						box[i][j] = false;
						box[i][j + 1] = false;
						box[i + 2][j] = false;
						break;
					case 1: //From horizontal right-side-down to vertical low-side-right
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (i == 19)
							return false;
						if (board[i + 1][j + 1])
							return false;
						if (board[i + 2][j + 1])
							return false;
						//Perform transformation
						box[i + 1][j + 1] = true;
						box[i + 2][j + 1] = true;
						box[i][j] = false;
						box[i + 1][j + 2] = false;
						break;
					case 2: //From vertical top-side-right to horizontal left-side-down
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//If is behind the left wall, try to move right
						if (j == 1){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j - 1])
							return false;
						if (board[i][j - 2])
							return false;
						//Perform transformation
						box[i][j - 1] = true;
						box[i][j - 2] = true;
						box[i + 2][j] = false;
						box[i + 2][j - 1] = false;
						break;
				}
				break;
			case Values.PIECE_2:
				//Switch new rotation state
				switch (rotation - 1){
					case -1: //From horizontal right-side-up to vertical top-side-left
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (i == 19)
							return false;
						if (board[i][j - 1])
							return false;
						if (board[i + 2][j])
							return false;
						//Perform transformation
						box[i][j - 1] = true;
						box[i + 2][j] = true;
						box[i + 1][j - 1] = false;
						box[i + 1][j - 2] = false;
						break;
					case 0: //From vertical bottom-side-right to horizontal right-side-up
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move lright
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j + 1])
							return false;
						if (board[i + 1][j + 1])
							return false;
						if (board[i + 1][j - 1])
							return false;
						//Perform transformation
						box[i][j + 1] = true;
						box[i + 1][j + 1] = true;
						box[i + 1][j - 1] = true;
						box[i][j] = false;
						box[i + 2][j] = false;
						box[i + 2][j + 1] = false;
						break;
					case 1: //From horizontal left-side-down to vertical low-side-right
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (i == 19)
							return false;
						if (board[i + i][j + 1])
							return false;
						if (board[i + 2][j + 1])
							return false;
						if (board[i + 2][j + 2])
							return false;
						//Perform transformation
						box[i + 1][j + 1] = true;
						box[i + 2][j + 1] = true;
						box[i + 2][j + 2] = true;
						box[i][j] = false;
						box[i + 1][j] = false;
						box[i][j + 2] = false;
						break;
					case 2: //From vertical top-side-left to horizontal left-side-down
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move right
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j - 1])
							return false;
						if (board[i + 1][j - 1])
							return false;
						//Perform transformation
						box[i][j - 1] = true;
						box[i + 1][j - 1] = true;
						box[i + 1][j + 1] = false;
						box[i + 2][j + 1] = false;
						break;
				}
				break;

			case Values.PIECE_3:
				//Is the cube, so, do nothing!
				break;

			case Values.PIECE_4: // inverted 'Z'- shaped
				//Switch new rotation state
				switch (rotation - 1){

					case 1:
					case -1: //From upleft & downright to leftup & rghtdown
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (board[i + 1][j + 1])
							return false;
						if (board[i + 2][j + 1])
							return false;
						//Perform transformation
						box[i + 1][j + 1] = true;
						box[i + 2][j + 1] = true;
						box[i][j + 1] = false;
						box[i + 1][j - 1] = false;
						break;

					case 2:
					case 0: //From leftup & rightdown to upleft & downright 
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move right
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j + 1])
							return false;
						if (board[1 + 1][j - 1])
							return false;				
						//Perform transformation
						box[i][j + 1] = true;
						box[i + 1][j - 1] = true;
						box[i + 1][j + 1] = false;
						box[i + 2][j + 1] = false;
						break;

				}
				break;

			case Values.PIECE_5: // 'Z'- shaped
				//Switch new rotation state
				switch (rotation - 1){

					case 1:
					case -1: //From downleft & upright to rightup & leftdown
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (board[i + 2][j + 1])
							return false;
						if (board[i][j + 2])
							return false;
						//Perform transformation
						box[i + 2][j + 1] = true;
						box[i][j + 2] = true;
						box[i][j] = false;
						box[i][j + 1] = false;
						break;

					case 2:
					case 0: //From rightup & leftdown to downleft & upright
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move right
						if (j == 1){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i][j - 2])
							return false;
						if (board[1][j - 1])
							return false;				
						//Perform transformation
						box[i][j - 2] = true;
						box[i][j - 1] = true;
						box[i][j] = false;
						box[i + 2][j - 1] = false;
						break;
				}
				break;


			case Values.PIECE_6: // 'T'- shaped
				//Switch new rotation state
				switch (rotation - 1){
					case -1: //From top faced to right faced
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 18)
							return false;
						if (board[i + 2][j])
							return false;
						//Perform transformation
						box[i + 2][j] = true;
						box[i + 1][j + 1] = false;
						break;
					case 0: //From right faced to bottom faced
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move right
						if (j == 0){
							if (!moveRight())
								return false;
							j = j + 1;
						}
						//Check availability
						if (board[i + 1][j - 1])
							return false;
						//Perform transformation
						box[i + 1][j - 1] = true;
						box[i + 2][j] = false;
						break;
					case 1: //From bottom faced to left faced
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 9){
								j = 0;
								i++;
							}
						}
						//Check availability
						if (i == 0)
							return false;
						if (board[i - 1][j + 1])
							return false;
						//Perform transformation
						box[i - 1][j + 1] = true;
						box[i][j] = false;
						break;
					case 2: //From left faced to top faced
						//Find the first occupied box
						while (!box[i][j]){
							j++;
							if (j == 10){
								j = 0;
								i++;
							}
						}
						//If its behind a wall, try to move left
						if (j == 9){
							if (!moveLeft())
								return false;
							j = j - 1;
						}
						//Check availability
						if (board[i + 1][j + 1])
							return false;
						//Perform transformation
						box[i + 1][j + 1] = true;
						box[i][j] = false;
						break;
				}
				break;
		}
		//Change rotation state
		rotation--;
		if (rotation == -1)
			rotation = 3;
		return true;
	}
}
