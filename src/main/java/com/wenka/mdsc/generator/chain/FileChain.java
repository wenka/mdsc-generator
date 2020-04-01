package com.wenka.mdsc.generator.chain;

import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.service.GenFileService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  下午 02:45
 * @description:
 */
public class FileChain {

    private List<GenFileService> fileChain;
    private int index = 0;

    public FileChain() {
        fileChain = new ArrayList<>(5);
    }

    public FileChain addChain(GenFileService genFileService) {
        this.fileChain.add(genFileService);
        return this;
    }

    public FileChain addChain(List<GenFileService> genFileServiceList) {
        for (GenFileService genFileService : genFileServiceList) {
            this.fileChain.add(genFileService);
        }
        return this;
    }


    public void execute(TableInfo tableInfo) {
        if (index == this.fileChain.size()) {
            return;
        }
        GenFileService genFileService = this.fileChain.get(index);
        index++;
        genFileService.writeFile(tableInfo, this);
    }
}
