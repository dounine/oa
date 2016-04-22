package net.yasion.common.service;

import java.io.File;
import java.util.List;

public interface IUEditorService {

	public List<File> getFiles(String realpath, List<File> files);

}