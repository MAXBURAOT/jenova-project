package com.angelis.tera.tools;

import java.io.File;

import com.angelis.tera.tools.extractor.MainCreatureExtractor;
import com.angelis.tera.tools.extractor.MainGatherExtractor;
import com.angelis.tera.tools.extractor.MainItemExtractor;

public class MainTools {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Not enough arguments");
            System.exit(1);
        }
        
        try {
            new MainCreatureExtractor(new File(args[0]));
            new MainGatherExtractor(new File(args[0]));
            new MainItemExtractor(new File(args[0]));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
