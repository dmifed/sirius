import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author dmifed
 */
public class Main {
    public static void main(String[] args) throws Exception{
        String rootPath = "X:\\_project\\Петрович\\Таблички\\2021\\08 11 21 Панели Сириус\\crop_pic";
        File root = new File(rootPath);
        PathFinder pathFinder = new PathFinder(root);
        pathFinder.checkSequence(root);

/*        Map<String, List<String>> mapOfListFilesInEachFolder = new TreeMap<>();
        mapOfListFilesInEachFolder = pathFinder.getMapOfListFilesInEachFolder(root, mapOfListFilesInEachFolder);
        Map<String, List<String>> mapOfPairsImages = pathFinder.getMapOfPairsImages(mapOfListFilesInEachFolder);
        pathFinder.saveMapOfListFilesInEachFolderToFile(mapOfListFilesInEachFolder);
        pathFinder.saveMap(mapOfPairsImages);*/
    }
}
