package com.clonecode.inssagram.service;

import com.clonecode.inssagram.domain.Image;
import com.clonecode.inssagram.domain.Post;
import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.dto.request.PostRequestDto;
import com.clonecode.inssagram.dto.response.*;
import com.clonecode.inssagram.exception.EntityNotFoundException;
import com.clonecode.inssagram.exception.InvalidValueException;
import com.clonecode.inssagram.global.error.ErrorCode;
import com.clonecode.inssagram.jwt.TokenProvider;
import com.clonecode.inssagram.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final StorageService storageService;
    private final HeartService heartService;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public ResponseDto<?> writePost(User user, PostRequestDto requestDto, List<MultipartFile> imageFileList) {
        List<String> imageUrlList = storageService.uploadFile(imageFileList, "posts/");      //이미지 S3에 업로드
        Post post = new Post(user, requestDto, 0L);
        postRepository.save(post);      //게시물 db에 저장
        List<Image> collect = imageUrlList.stream().map(image -> new Image(image, post)).collect(Collectors.toList());   //String List를 Image List로 변환
        imageRepository.saveAll(collect);
        Long commentNum = commentRepository.countByPost(post);       //댓글 수 게시물id로 세서 찾기 - 게시물을 저장 먼저 해야 id가 생겨서 count 가능
        return ResponseDto.success(PostCreateResponseDto.builder()      //responseDto 돌려주기
                .post(post)
                .imageUrl(collect.get(0).getImageUrl())
                .heartNum(0L)
                .commentNum(commentNum)
                .build());
    }

    //전체 게시글 조회
    @Transactional
    public ResponseDto<?> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();      //생성시간 늦은 순으로 정렬해서 찾기
        List<PostAllResponseDto> postAllResponseDtoList = new ArrayList<>();    //PostAllResponseDto 빈 array 생성
        User user = tokenProvider.getUserFromAuthentication();
        for (Post post : posts) {       //각 포스트마다 돌면서
            Long commentNum = commentRepository.countByPost(post);       //댓글 수 게시물id로 세서 찾기
            postAllResponseDtoList.add(PostAllResponseDto.builder()//빈 array에 responseDto 만들어서 넣어주기
                    .isHeart(heartService.isHeart(post.getId(), user))
                    .post(post)
                    .heartNum(post.getHeartNum())
                    .commentNum(commentNum)
                    .build());
        }
        return ResponseDto.success(postAllResponseDtoList);      //array 돌려주기
    }

    //상세 게시글 조회
    @Transactional
    public ResponseDto<?> getOnePost(Long postId) {
        Post post = postRepository.findById(postId)         //게시물 찾기
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));      //없을 시 찾을 수 없음
        User user = tokenProvider.getUserFromAuthentication();
        Long commentNum = commentRepository.countByPost(post);       //댓글 수 게시물id로 세서 찾기
        return ResponseDto.success(PostDetailResponseDto.builder()      //responseDto 돌려주기
                .post(post)
                .heartNum(post.getHeartNum())
                .commentNum(commentNum)
                .isHeart(heartService.isHeart(post.getId(), user))
                .build());
    }

    //게시글 수정
    @Transactional
    public ResponseDto<?> updateOnePost(Long postId, User user, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId)      //게시물 찾기
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));       //없을 시 찾을 수 없음
        if (!user.getId().equals(post.getUser().getId())) {        //로그인한 사용자=게시물 작성자인지 확인
            throw new InvalidValueException(ErrorCode.POST_UNAUTHORIZED);    //아닐 시 권한 없음
        }
        post.update(requestDto);      //맞을 시 업데이트
        return ResponseDto.success(new PostUpdateResponseDto(post));
    }

    //게시글 삭제
    public void deleteOnePost(Long postId, User user) {
        Post post = postRepository.findById(postId)     //게시물 찾기
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));      //없을 시 찾을 수 없음
        if (!user.getId().equals(post.getUser().getId())) {        //로그인한 사용자=게시물 작성자인지 확인
            throw new InvalidValueException(ErrorCode.POST_UNAUTHORIZED);   //아닐 시 권한 없음
        }
        postRepository.deleteById(postId);      //맞을 시 삭제
    }

    // 유저가 쓴 글 조회
    @Transactional
    public ResponseDto<?> getMyPage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        MyPageResponseDto myPageResponseDto = MyPageResponseDto.builder()
                .user(user)
                .postTotalNum(postRepository.countByUserId(userId))
                .heartTotalNum(heartService.totalHeartsByUser(userId))
                .contentList(getMyPostList(user))
                .build();
        return ResponseDto.success(myPageResponseDto);
    }

    public List<PostCreateResponseDto> getMyPostList(User user) {
        List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(user);
        if (!(posts == null)) {
            List<PostCreateResponseDto> contentsList = new ArrayList<>();
            for (Post post : posts) {
                Long commentNum = commentRepository.countByPost(post);
                PostCreateResponseDto mypostResponseDto = PostCreateResponseDto.builder()
                        .post(post)
                        .imageUrl(post.getImageList().get(0).getImageUrl())
                        .heartNum(post.getHeartNum())
                        .commentNum(commentNum)
                        .build();
                contentsList.add(mypostResponseDto);
            }
            return contentsList;
        } else {
            return null;
        }
    }

}
