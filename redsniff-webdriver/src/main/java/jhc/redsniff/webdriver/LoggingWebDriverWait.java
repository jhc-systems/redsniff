package jhc.redsniff.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class LoggingWebDriverWait extends WebDriverWait {
    
    
    LoggingWebDriverWait(WebDriver driver, long timeOutInSeconds) {
        super(driver, timeOutInSeconds);
    }

    @Override
    public <V> V until(Function<? super WebDriver, V> isTrue) {
        return super.until(loggingFunction(isTrue));
    }

    private <V, T> Function<T, V> loggingFunction(final Function<T, V> wrapped){
        return new Function<T, V>() {
            int calledNum=0;
            public V apply(T input){
                long startTimeMillis = System.currentTimeMillis();
                V apply = wrapped.apply(input);
                if(calledNum>0)
                    log("Called " + wrapped.toString() + " again for " + calledNum + "th time - took" +((System.currentTimeMillis() - startTimeMillis)/1000d) + "::" +(apply==null?"NULL":apply.toString()));
                calledNum++;
                return apply;
            }
			@Override
			public String toString() {
				return wrapped.toString();
			}
            
        };
        
    }
    
    
    private void log(String message){
        //System.out.println(message);
    }
}