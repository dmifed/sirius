import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author dmifed
 */

public class PathFinder {
    private List<String> pathsAndNames;
    private List<String> paths;
    private List<String> names;
    private List<String> namePairs;
    private final String tab = "\t";
    private File root;

    public PathFinder(String rootPath) {
        pathsAndNames = new ArrayList<>();
        namePairs = new ArrayList<>();
        paths = new ArrayList<>();
        names = new ArrayList<>();
        root = new File(rootPath);
    }

    public void findPathsAndNames(File root){
        if(!root.isFile()){
            if(root.getName().startsWith("S")){
                File[] files = root.listFiles();
                if(files != null){
                    Map<String, List<String>> pairNames = new HashMap<>();
                    for(File file : files){
                        String nameStarts = file.getName().substring(0, 2);
                        String path = file.getAbsolutePath();
                        String name = file.getName();
                        path = path.substring(0, path.length() - name.length());
                        if(pairNames.containsKey(nameStarts)){
                            pairNames.get(nameStarts).add(name + "%" + path);
                        }else {
                            List<String> pairNamesPaths = new ArrayList<>();

                            pairNamesPaths.add(name + "%" + path);
                            pairNames.put(nameStarts, pairNamesPaths);
                        }
                    }
                    for(Map.Entry<String, List<String>> entry : pairNames.entrySet()){
                        List<String> paths = entry.getValue();
                        if(paths.size() > 1){
                            namePairs.addAll(paths);
                        }
                    }

                }
            }



            File[] files = root.listFiles();
            for(File file : files){
                findPathsAndNames(file);
            }
        }else {
            String path = root.getAbsolutePath();
            String name = root.getName();
            name = NameCorrector.correct(name);
            pathsAndNames.add(name + tab + path + "\r\n");
        }
    }

    public void saveList(File root){
        Path path = Paths.get(root.getAbsolutePath() + "/result.txt");
        try {
            Files.createFile(path);
            PrintWriter printWriter = new PrintWriter(path.toFile().getAbsolutePath());
            pathsAndNames.forEach(printWriter::print);
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void savePairsList(File root){
        Stack<Character> stack = new Stack<>();
        //6_Мозаика Corsa Deco Mist.jpg%X:\_project\Петрович\Таблички\2021\08 11 21 Панели Сириус\crop_pic\СЗФО\Выборг\S10\
        namePairs.sort((o1, o2) -> {
            String head1 = o1.substring(0, 2);
            String tail1 = o1.split("%")[1];
            String head2 = o2.substring(0, 2);
            String tail2 = o2.split("%")[1];
            if(head1.equals(head2) && tail1.equals(tail2)) return 0;
            return 1;
        });
        for(String s : namePairs){
            if(!s.equals("\r\n")){
                System.out.println(s);
            }

        }
        Path path = Paths.get(root.getAbsolutePath() + "/pairs.txt");
        try {
            Files.createFile(path);
            PrintWriter printWriter = new PrintWriter(path.toFile().getAbsolutePath());
            namePairs.forEach(printWriter::print);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public File getRoot() {
        return root;
    }
}
