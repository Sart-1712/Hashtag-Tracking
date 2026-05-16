package hashtagManagement;


abstract class Service {
    //defining an astract class (to be used in the future).
    abstract void execute() throws Exception;

    void log() {
        System.out.println("Service running...");
    }
}