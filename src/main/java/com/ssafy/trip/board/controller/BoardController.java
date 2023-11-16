package com.ssafy.trip.board.controller;

import com.ssafy.trip.board.model.Board;
import com.ssafy.trip.board.model.FileInfo;
import com.ssafy.trip.board.model.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/board")
@Api(tags = {"게시판 컨트롤러 API V1"})
@Slf4j
public class BoardController {
    @Value("${file.path.upload-default}")
    private String uploadPath;

    @Value("${file.path.upload-images}")
    private String uploadImagePath;

    @Value("${file.path.upload-files}")
    private String uploadFilePath;

    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        super();
        this.boardService = boardService;
    }

    @ApiOperation(value = "글 등록", notes = "글 정보를 받아 등록")
    @PostMapping("/write")
    public ResponseEntity<?> write(Board board, @RequestParam(value = "upfile", required = false) MultipartFile[] files) {
        log.debug("write boardDto : {}", board);

//		log 관련 설정.
        log.debug("uploadPath : {}, uploadImagePath : {}, uploadFilePath : {}", uploadPath, uploadImagePath, uploadFilePath);
        try {
            if (!files[0].isEmpty()) {
                log.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
    //			String realPath = servletContext.getRealPath(UPLOAD_PATH);
    //			String realPath = servletContext.getRealPath("/resources/img");
                String today = new SimpleDateFormat("yyMMdd").format(new Date());
                String saveFolder = uploadPath + File.separator + today;
                log.debug("저장 폴더 : {}", saveFolder);
                File folder = new File(saveFolder);
                if (!folder.exists())
                    folder.mkdirs();
                List<FileInfo> fileInfos = new ArrayList<FileInfo>();
                for (MultipartFile mfile : files) {
                    FileInfo fileInfo = new FileInfo();
                    String originalFileName = mfile.getOriginalFilename();
                    if (!originalFileName.isEmpty()) {
                        String saveFileName = UUID.randomUUID().toString()
                                + originalFileName.substring(originalFileName.lastIndexOf('.'));
                        fileInfo.setSaveFolder(today);
                        fileInfo.setOriginalFile(originalFileName);
                        fileInfo.setSaveFile(saveFileName);
                        log.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
                        mfile.transferTo(new File(folder, saveFileName));
                    }
                    fileInfos.add(fileInfo);
                }
                board.setFileInfos(fileInfos);
            }

            boardService.writeArticle(board);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e){
            e.printStackTrace();
            return exceptionHandling(e);
        }
    }


    @ApiOperation(value = "글 목록", notes = "조회 조건에 따라 글 목록 반환")
    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam Map<String, String> map) {
        log.debug("list parameter pgno : {}", map.get("pgno"));
        try {
            List<Board> boards = boardService.listArticle(map);
            return ResponseEntity.status(HttpStatus.OK).body(boards);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
        
    }


    @ApiOperation(value = "글 읽기", notes = "특정 글 내용 확인")
    @GetMapping("/{articleNo}")
    public ResponseEntity<?> view(@PathVariable int articleNo) {
        log.debug("view articleNo : {}", articleNo);
        try{
            Board board = boardService.getArticle(articleNo);
            boardService.updateHit(articleNo);
            return ResponseEntity.status(HttpStatus.OK).body(board);
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }


    @ApiOperation(value = "글 수정", notes = "특정 글 내용 수정")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(Board board) {

        log.debug("modify boardDto : {}", board);
        try {
            boardService.modifyArticle(board);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    @ApiOperation(value = "글 삭제", notes = "선택된 글 삭제")
    @DeleteMapping("/{articleNo}")
    public ResponseEntity<?> delete(@PathVariable int articleNo) {
        log.debug("delete articleNo : {}", articleNo);
//		boardService.deleteArticle(articleNo, servletContext.getRealPath(UPLOAD_PATH));
        try {
            boardService.deleteArticle(articleNo, uploadPath);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    private ResponseEntity<String> exceptionHandling(Exception e) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<String>("errors : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
