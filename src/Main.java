import java.io.File;

/**
 * @author dmifed
 */
public class Main {
    public static void main(String[] args) {
        String rootPath = "X:\\_project\\Петрович\\Таблички\\2021\\08 11 21 Панели Сириус\\crop_pic";
        PathFinder pathFinder = new PathFinder(rootPath);

        pathFinder.findPathsAndNames(pathFinder.getRoot());
        pathFinder.saveList(pathFinder.getRoot());
        pathFinder.savePairsList(pathFinder.getRoot());
    }
}
