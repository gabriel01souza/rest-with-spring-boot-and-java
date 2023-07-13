package br.com.erudio.data.vo.v1;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadFileResponseVO  implements Serializable {

    private static final long serialVerionUID = 1L;

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponseVO(){}

    public UploadFileResponseVO(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
