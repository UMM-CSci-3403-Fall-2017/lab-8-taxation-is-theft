package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    @Override
    public int minimumPairwiseDistance(int[] values) {
        int numThreads = 4;
    	
    	Thread[] threads = new Thread[numThreads];
    	Answer ans = new Answer();
    	int seperator1 = 0;
    	int seperator2 = (values.length/2);
    	
    	for(int i = 0; i < numThreads; i++){
    		
    		threads[i] = new Thread(new MinimumPairwiseDistanceHelper(values, seperator1, ((int) seperator2), ans, i));
    		threads[i].start();
    		
    		if(i==0) seperator1 = seperator2;
    		seperator2 = values.length;
    	}
    	
    	for(int i = 0; i<numThreads; i++){
    		try {
				threads[i].join();
			} catch (InterruptedException e) {
				System.err.print(e);
			}
    	}
    	
    	return ans.getAnswer();
    }

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
		
		private MinimumPairwiseDistanceHelper(int[] values, int seperator1, int seperator2, Answer answer, int type){
			this.values = values;
			this.seperator1 = seperator1;
			this.seperator2 = seperator2;
			this.answer = answer;
			this.type = type;
		}
		@Override
		public void run() {
			int result = Integer.MAX_VALUE;
	        if(type == 0){
	        	for (int i = seperator1; i < seperator2; ++i) {
		            for (int j = seperator1; j < i; ++j) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
	        } else if (type == 1){
	        	for (int i = seperator1; i < seperator2; ++i) {
		            for (int j = 0; j+(values.length/2) < i; ++j) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
	        } else if (type == 2){
	        	for (int i = seperator1; i < seperator2; ++i) {
		            for (int j = seperator1; j < i; ++j) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
	        } else if (type == 3){
	        	for (int j = 0; j < seperator1; ++j) {
		            for (int i = seperator1; i < j + (values.length/2); ++i) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
	        }
	        if(answer.getAnswer() > result){
	        	answer.setAnswer(result);
	        }
		}
    	
    }
}

