package net.yasion.common.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.yasion.common.service.IUEditorService;

import org.springframework.stereotype.Service;

@Service("uEditorService")
public class UEditorServiceImpl implements IUEditorService {

	@Override
	public List<File> getFiles(String realpath, List<File> files) {
		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					getFiles(file.getAbsolutePath(), files);
				} else {
					if (!getFileType(file.getName()).equals("")) {
						files.add(file);
					}
				}
			}
		}
		return files;
	}

	protected String getFileType(String fileName) {
		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
		Iterator<String> type = Arrays.asList(fileType).iterator();
		while (type.hasNext()) {
			String t = type.next();
			if (fileName.toLowerCase().endsWith(t)) {
				return t;
			}
		}
		return "";
	}
}