package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    @Override
    public int minimumPairwiseDistance(int[] values) {
        int numThreads = 4;
    	
    	Thread[] threads = new Thread[numThreads];
    	Answer ans = new Answer();
    	int seperator1 = 0; //The "lower bound"
    	int seperator2 = (values.length/2); //The "upper bound"
    	
    	for(int i = 0; i < numThreads; i++){
    		
    		threads[i] = new Thread(new MinimumPairwiseDistanceHelper(values, seperator1, ((int) seperator2), ans, i));
    		threads[i].start();
    		
    		//change the bounds for the next 3 cases:
    		if(i==0) {
    			seperator1 = seperator2;
    			seperator2 = values.length;
    		}
    	}
    	
    	//wait for all threads to finish
    	for(int i = 0; i<numThreads; i++){
    		try {
				threads[i].join();
			} catch (InterruptedException e) {
				System.err.print(e);
			}
    	}
    	
    	return ans.getAnswer();
    }

    /**
     * 
     * @authors Xaitheng Yang, Marshall Hoffmann
     * Contains the result of ThreadedMinimumPairwiseDistance's threads
     * Begins with max value so it will always be replaced by the first value found (it will always be larger than the value found)
     * setAnswer is synchronized to avoid issues with simultaneous calculations
     * answer contains the integer that is the lowest distance between any two values contained in an array
     */
    private class Answer{
    	private int answer = Integer.MAX_VALUE;
    	
    	public int getAnswer(){
    		return answer;
    	}
    	
    	public synchronized void setAnswer(int newAnswer){
    		answer = newAnswer;
    	}
    }
    
    private class MinimumPairwiseDistanceHelper implements Runnable{

		int[] values;
		int seperator1;
		int seperator2;
		Answer answer;
		int type;
		
		/**
		 * 
		 * @param values : The array of integers to be searched through
		 * @param seperator1 : The lower bound
		 * @param seperator2 : The upper bound
		 * @param answer : The object that contains the answer that all the threads will share
		 * @param type : integer to designate which sections of the array will be compared to eachother
		 */
		private MinimumPairwiseDistanceHelper(int[] values, int seperator1, int seperator2, Answer answer, int type){
			this.values = values;
			this.seperator1 = seperator1;
			this.seperator2 = seperator2;
			this.answer = answer;
			this.type = type;
		}
		@Override
		public void run() {
			//begins at max value so it can be replaced by any value (all values will be beneath it)
			int result = Integer.MAX_VALUE;
			
	        if(type == 0){ //Compares the first half of the array to itself
	        	for (int i = seperator1; i < seperator2; ++i) {
		            for (int j = seperator1; j < i; ++j) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
	        } else if (type == 1){ //Compares the first half of the list to the second half
	        	for (int i = seperator1; i < seperator2; ++i) {
		            for (int j = 0; j+(values.length/2) < i; ++j) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
	        } else if (type == 2){ //Compares the second half of the list to itself
	        	for (int i = seperator1; i < seperator2; ++i) {
		            for (int j = seperator1; j < i; ++j) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
	        } else if (type == 3){ //Compares the second half to the first half
	        	for (int j = 0; j < seperator1; ++j) {
		            for (int i = seperator1; i < j + (values.length/2); ++i) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
	        }
	        
	        //If the result of the specific case used is lower than the current answer, replace the current answer in the Answer object
	        if(answer.getAnswer() > result){
	        	answer.setAnswer(result);
	        }
		}
    	
    }
}

