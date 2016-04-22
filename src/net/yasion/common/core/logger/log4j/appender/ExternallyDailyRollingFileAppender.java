package net.yasion.common.core.logger.log4j.appender;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.yasion.common.utils.WebContextUtils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Priority;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

public class ExternallyDailyRollingFileAppender extends FileAppender {

	protected long maxFileSize = 0;

	protected int maxBackupIndex = 0;

	private String datePattern = null;

	private String scheduledFilename = null;

	private long nextRollover = 0;

	private int nowIndex = 0;// 当maxFileSize没有设置时候需要0

	private Date nowDate = new Date();

	/** 进行精确的优先级比较,就是说只有相当才符合 */
	private boolean exactly = true;

	private SimpleDateFormat sdf = null;

	public ExternallyDailyRollingFileAppender() {
		super();
	}

	public ExternallyDailyRollingFileAppender(Layout layout, String filename, boolean append) throws IOException {
		super(layout, filename, append);
	}

	public ExternallyDailyRollingFileAppender(Layout layout, String filename) throws IOException {
		super(layout, filename);
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public boolean isExactly() {
		return exactly;
	}

	public void setExactly(boolean exactly) {
		this.exactly = exactly;
	}

	public int getMaxBackupIndex() {
		return maxBackupIndex;
	}

	public void setMaxBackupIndex(int maxBackupIndex) {
		this.maxBackupIndex = maxBackupIndex;
	}

	public long getMaximumFileSize() {
		return maxFileSize;
	}

	public void setMaximumFileSize(long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public void setMaxFileSize(String value) {
		maxFileSize = OptionConverter.toFileSize(value, maxFileSize + 1);// 加上没有指定命名的日志文件
	}

	public void activateOptions() {
		if (StringUtils.isNotBlank(fileName)) {
			try {
				boolean isWebContextPath = WebContextUtils.isWebContextPath(fileName);
				if (isWebContextPath) {
					fileName = WebContextUtils.getWebContextRealPath(fileName);
				}
				setFile(fileName, fileAppend, bufferedIO, bufferSize);
				if (StringUtils.isNotBlank(datePattern)) {// 初始化日期的数据
					sdf = new SimpleDateFormat(datePattern);
					File file = new File(fileName);
					nowDate.setTime(file.lastModified());// 文件时间作为初始时间
					scheduledFilename = getVaildDateFileName(fileName, nowDate, 0);
				}
				// 获取最近nowIndex
				nowIndex = getInitNowIndex();
			} catch (java.io.IOException e) {
				errorHandler.error("setFile(" + fileName + "," + fileAppend + ") call failed.", e, ErrorCode.FILE_OPEN_FAILURE);
			} catch (Exception e) {
				errorHandler.error("activateOptions call failed.", e, ErrorCode.FILE_OPEN_FAILURE);
			}
		} else {
			LogLog.warn("File option not set for appender [" + name + "].");
			LogLog.warn("Are you using FileAppender instead of ConsoleAppender?");
		}
	}

	protected String getVaildDateFileName(String fileName, Date date, int index) {
		return getVaildDateFileName(fileName, date, index, false);
	}

	protected String getVaildDateFileName(String fileName, Date date, int index, boolean surplus) {
		String vaildName = "";
		String baseName = FilenameUtils.getBaseName(fileName);
		String extension = FilenameUtils.getExtension(fileName);
		String fullPath = FilenameUtils.getFullPath(fileName);
		vaildName = (fullPath + baseName + (null == sdf ? "" : (null == date ? "" : "_" + (sdf.format(date)))) + ((0 < index && !surplus) ? "_" + index : "") + (surplus ? (0 < maxFileSize ? "_0" : "_") : "") + "." + extension);
		return vaildName;
	}

	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		if (isExactly()) {
			// 只判断是否相等，而不判断优先级
			return this.getThreshold().equals(priority);
		} else {
			return super.isAsSevereAsThreshold(priority);
		}
	}

	// synchronization not necessary since doAppend is alreasy synched
	protected void rollOver() {
		long size = ((CountingQuietWriter) qw).getCount();
		Date fnowDate = new Date();
		String datedFilename = getVaildDateFileName(fileName, fnowDate, 0);// 忽略index来比较才能准确
		boolean isRun = false;
		if ((0 < maxFileSize) && (size >= maxFileSize && size >= nextRollover)) {
			rollOverByMaxFileSize();
			isRun = true;
		} else if ((StringUtils.isNotBlank(datePattern) && !scheduledFilename.equals(datedFilename))) {
			rollOverByScheduled(fnowDate);// 指定时间,避免误差
			isRun = true;
		}
		if (isRun && 0 < maxBackupIndex) {
			removeRollOver();
		}
	}

	protected void rollOverByMaxFileSize() {
		File target = null;
		File file = null;
		if (qw != null) {
			long size = ((CountingQuietWriter) qw).getCount();
			LogLog.debug("rolling over count=" + size);
			// if operation fails, do not roll again until
			// maxFileSize more bytes are written
			nextRollover = size + maxFileSize;
		}
		LogLog.debug("maxBackupIndex=" + maxBackupIndex);
		boolean renameSucceeded = true;
		// Map {(maxBackupIndex - 1), ..., 2, 1} to {maxBackupIndex, ..., 3, 2}
		// nowIndx越小,表示文件越新
		for (int i = nowIndex; i >= 1 && renameSucceeded; i--) {
			file = new File(getVaildDateFileName(fileName, nowDate, i));
			if (file.exists()) {
				target = new File(getVaildDateFileName(fileName, nowDate, i + 1));
				LogLog.debug("Renaming file " + file + " to " + target);
				renameSucceeded = file.renameTo(target);
			}
		}
		if (renameSucceeded) {
			// Rename fileName to fileName.1
			target = new File(getVaildDateFileName(fileName, nowDate, 1));
			this.closeFile(); // keep windows happy.
			file = new File(fileName);
			LogLog.debug("Renaming file " + file + " to " + target);
			renameSucceeded = file.renameTo(target);
			// 如果重命名失败,重新打开文件去插入(如果append = true)
			if (!renameSucceeded) {
				try {
					this.setFile(fileName, true, bufferedIO, bufferSize);
				} catch (IOException e) {
					if (e instanceof InterruptedIOException) {
						Thread.currentThread().interrupt();
					}
					LogLog.error("setFile(" + fileName + ", true) call failed.", e);
				}
			}
		}
		if (renameSucceeded) {// 如果全部重命名操作,则执行下面操作
			try {
				// This will also close the file. This is OK since multiple
				// close operations are safe.
				this.setFile(fileName, false, bufferedIO, bufferSize);
				nextRollover = 0;
				nowIndex += 1;// 增加一个数值
			} catch (IOException e) {
				if (e instanceof InterruptedIOException) {
					Thread.currentThread().interrupt();
				}
				LogLog.error("setFile(" + fileName + ", false) call failed.", e);
			}
		}
	}

	protected void rollOverByScheduled(Date fnowDate) {// 指定时间,避免误差
		/* Compute filename, but only if datePattern is specified */
		if (StringUtils.isBlank(datePattern)) {
			errorHandler.error("Missing DatePattern option in rollOver().");
			return;
		}
		String datedFilename = getVaildDateFileName(fileName, fnowDate, 0);// 忽略index来比较才能准确
		// It is too early to roll over because we are still within the
		// bounds of the current interval. Rollover will occur once the
		// next interval is reached.
		if (scheduledFilename.equals(datedFilename)) {
			return;
		}
		// 关闭现在操作的文件,重命名到datedFilename
		this.closeFile();
		File file = new File(fileName);
		String realDatedFilename = getVaildDateFileName(fileName, nowDate, (0 < maxFileSize ? 1 : 0));// 重置日期时候必须指定index为1
		File target = new File(realDatedFilename);
		boolean result = true;
		if (target.exists()) {// 已经存在,就要把额外的加回去
			// target.delete();
			String surplusDatedFilename = getVaildDateFileName(fileName, nowDate, 0, true);
			target = new File(surplusDatedFilename);
			result = file.renameTo(target);
		} else {
			result = file.renameTo(target);
		}
		if (result) {
			LogLog.debug(fileName + " -> " + target.getAbsolutePath());
		} else {
			LogLog.error("Failed to rename [" + fileName + "] to [" + target.getAbsolutePath() + "].");
		}
		try {
			// This will also close the file. This is OK since multiple
			// close operations are safe.
			this.setFile(fileName, true, this.bufferedIO, this.bufferSize);
		} catch (IOException e) {
			errorHandler.error("setFile(" + fileName + ", true) call failed.");
		}
		scheduledFilename = datedFilename;
		nowDate = fnowDate;
		if (0 < maxFileSize) {
			nowIndex = 0;// 重置滚动index
		}
	}

	protected List<File> getLogFiles() {
		final String realFilePath = this.fileName;
		File file = new File(realFilePath);
		File parentFile = file.getParentFile();
		File[] files = parentFile.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				File targetFile = new File(dir, name);
				if (targetFile.isFile()) {
					String realFileBaseName = FilenameUtils.getBaseName(realFilePath);
					String fileBaseName = FilenameUtils.getBaseName(name);
					return StringUtils.startsWith(fileBaseName, realFileBaseName);
				} else {
					return false;
				}
			}
		});
		return ArrayUtils.isEmpty(files) ? new ArrayList<File>() : new ArrayList<File>(Arrays.asList(files));
	}

	protected List<File> getDateLogFiles() {
		if (StringUtils.isNotBlank(datePattern)) {
			final String realFilePath = this.fileName;
			File file = new File(realFilePath);
			File parentFile = file.getParentFile();
			File[] files = parentFile.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					File targetFile = new File(dir, name);
					if (targetFile.isFile()) {
						String nowTime = sdf.format(new Date());
						String realFileBaseName = FilenameUtils.getBaseName(realFilePath);
						String fileBaseName = FilenameUtils.getBaseName(name);
						return (StringUtils.startsWith(fileBaseName, realFileBaseName) && StringUtils.contains(fileBaseName, nowTime));
					} else {
						return false;
					}
				}
			});
			return ArrayUtils.isEmpty(files) ? new ArrayList<File>() : new ArrayList<File>(Arrays.asList(files));
		} else {
			return this.getLogFiles();
		}
	}

	protected int getInitNowIndex() {
		int index = 0;
		List<File> logFiles = getDateLogFiles();
		Set<File> sortedFileSet = new TreeSet<File>(new Comparator<File>() {

			@Override
			public int compare(File first, File second) {
				String firstName = first.getName();
				String secondName = second.getName();
				String firstBaseName = FilenameUtils.getBaseName(firstName);
				String secondBaseName = FilenameUtils.getBaseName(secondName);
				int firstIndexOf = StringUtils.lastIndexOf(firstBaseName, "_");
				int secondIndexOf = StringUtils.lastIndexOf(secondBaseName, "_");
				int firstFileIndex = (0 < firstIndexOf ? NumberUtils.toInt(StringUtils.substring(firstBaseName, firstIndexOf + 1)) : 0);
				int secondFileIndex = (0 < secondIndexOf ? NumberUtils.toInt(StringUtils.substring(secondBaseName, secondIndexOf + 1)) : 0);
				return (firstFileIndex > secondFileIndex ? -1 : (firstFileIndex == secondFileIndex ? 0 : 1));
			}
		});
		sortedFileSet.addAll(logFiles);
		File[] newFiles = sortedFileSet.toArray(new File[0]);
		if (ArrayUtils.isNotEmpty(newFiles)) {
			File indexFile = newFiles[0];// 现存最大值的file
			String indexName = indexFile.getName();
			String indexBaseName = FilenameUtils.getBaseName(indexName);
			int indexPos = StringUtils.lastIndexOf(indexBaseName, "_");
			int fileIndex = (0 < indexPos ? NumberUtils.toInt(StringUtils.substring(indexBaseName, indexPos + 1)) : 0);
			index = fileIndex;
		}
		return index;
	}

	protected void removeRollOver() {
		List<File> logFiles = getLogFiles();
		if (logFiles.size() > maxBackupIndex) {
			Set<File> sortedFileSet = new TreeSet<File>(new Comparator<File>() {

				@Override
				public int compare(File first, File second) {
					long firstLastModified = first.lastModified();
					long secondLastModified = second.lastModified();
					return (firstLastModified < secondLastModified ? -1 : (firstLastModified == secondLastModified ? 0 : 1));
				}
			});
			sortedFileSet.addAll(logFiles);
			File[] newFiles = sortedFileSet.toArray(new File[0]);
			if (ArrayUtils.isNotEmpty(newFiles)) {
				int deleteCount = newFiles.length - maxBackupIndex;
				for (int i = 0; i < deleteCount; i++) {
					File deleteFile = newFiles[i];
					if (null != deleteFile && deleteFile.exists()) {
						deleteFile.delete();
					}
				}
			}
		}
	}

	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
		super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
		if (append) {
			File f = new File(fileName);
			((CountingQuietWriter) qw).setCount(f.length());
		}
	}

	protected void setQWForFiles(Writer writer) {
		this.qw = new CountingQuietWriter(writer, errorHandler);
	}

	protected void subAppend(LoggingEvent event) {
		if (fileName != null && qw != null) {
			rollOver();
		}
		super.subAppend(event);
	}
}