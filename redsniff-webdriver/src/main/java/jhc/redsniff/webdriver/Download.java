package jhc.redsniff.webdriver;

public abstract class Download<T> {
    public abstract T getObject() throws Exception;
    public abstract void doneWith();
    
    protected String fileUrlFromWindowsPath(String pageFileName) {
        return "file:///" + pageFileName.replaceAll("\\\\","/");
    }

}
