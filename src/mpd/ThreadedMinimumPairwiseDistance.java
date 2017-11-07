package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    @Override
    public int minimumPairwiseDistance(int[] values) {
        int numThreads = 4;
    	
    	Thread[] threads = new Thread[numThreads];
    	Answer ans = new Answer();
    	int begin = 0;
    	int end = (values.length/2);
    	
    	for(int i = 0; i < numThreads; i++){
    		
    		threads[i] = new Thread(new MinimumPairwiseDistanceHelper(values, begin, ((int) end), ans, i));
    		threads[i].start();
    		
    		begin = end;
    		end = values.length;
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
		int begin;
		int end;
		Answer answer;
		int type;
		
		private MinimumPairwiseDistanceHelper(int[] values, int begin, int end, Answer answer, int type){
			this.values = values;
			this.begin = begin;
			this.end = end;
			this.answer = answer;
			this.type = type;
		}
		@Override
		public void run() {
			int result = Integer.MAX_VALUE;
	        if(type == 0){
	        	for (int i = begin; i < end; ++i) {
		            for (int j = begin; j < i; ++j) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
		        if(answer.getAnswer() > result){
		        	answer.setAnswer(result);
		        }
	        } else if (type == 1){
	        	for (int i = begin; i < end; ++i) {
		            for (int j = begin + (values.length/2); j < i; ++j) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
		        if(answer.getAnswer() > result){
		        	answer.setAnswer(result);
		        }
	        } else if (type == 2){
	        	for (int i = begin; i < end; ++i) {
		            for (int j = begin; j < i; ++j) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
		        if(answer.getAnswer() > result){
		        	answer.setAnswer(result);
		        }
	        } else if (type == 3){
	        	for (int j = begin + (values.length/2); j < end; ++j) {
		            for (int i = begin; i < j; ++i) {
		            	
		                int diff = Math.abs(values[i] - values[j]);
		                if (diff < result) {
		                    result = diff;
		                }
		            }
		        }
		        if(answer.getAnswer() > result){
		        	answer.setAnswer(result);
		        }
	        }
		}
    	
    }
}

