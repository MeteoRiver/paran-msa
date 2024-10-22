<h1>📚독서 소모임 공간 대여 서비스 Paranmanzang입니다.📚</h1>

<div align="center">
  <img src="https://github.com/user-attachments/assets/2b740bf6-e09b-4e56-8c3b-1dcc223dd33f" width="500"/>
</div>


<h2>📚프로젝트 소개</h2>

최근 독서 소모임과 같은 소규모 모임이 증가하면서, 해당 모임을 위한 공간 확보에 대한 수요도 함께 증가하고 있습니다.</br>
많은 사람들이 자신들의 소모임을 운영하기 위해 카페나 도서관을 찾지만, 이런 공간들은 자주 예약이 차거나 대화가 제한되는 문제점이 있습니다. 반면, 상업용으로 운영되는 공간들은 소모임을 위한 최적화된 예약 시스템이 부족한 경우가 많아 사용자들에게 불편을 주고 있습니다.</br>
이런 상황에서 독서 소모임 공간 대여 서비스 Paranmanzang은 소모임 주최자와 공간 대여 판매자를 연결하여 사용자가 간편하게 공간을 찾고 예약할 수 있는 플랫폼을 제공합니다. 또한, 소모임 구성원들이 채팅 기능을 통해 소통함으로써 모임 준비와 진행이 더욱 원활하게 이루어집니다.</br>
[www.paranmanzang.com](http://www.paranmanzang.com)</br>

<h2>📚팀 소개</h2>

<div align="center">
    <table>
        <tr>
            <td><img src="https://via.placeholder.com/100" alt="임청하👑"/><br/><a href="https://github.com/chenghaLim">임청하👑 (BackEnd)</a></td>
            <td><img src="https://via.placeholder.com/100" alt="강은석"/><br/><a href="https://github.com/MeteoRiver">강은석 (BackEnd)</a></td>
            <td><img src="https://via.placeholder.com/100" alt="김주영"/><br/><a href="https://github.com/Jyservice781">김주영 (FrontEnd)</a></td>
            <td><img src="https://via.placeholder.com/100" alt="송지현"/><br/><a href="https://github.com/Songj2">송지현 (BackEnd)</a></td>
        </tr>
    </table>
</div>


<h2>📚프로젝트 구조</h2>
<details><summary>FE</summary>
프론트 추가 예정


</details>

<details><summary>BE</summary> 
├── .gitignore</br>
├── .gitmodules</br>
├── Jenkinsfile</br>
├── back_output.txt</br>
├── build.gradle</br>
├── database</br>
│   └── docker-compose.yaml</br>
├── docker-compose.yaml</br>
├── file_source.json</br>
├── gradle</br>
│   └── wrapper</br>
│       ├── gradle-wrapper.jar</br>
│       └── gradle-wrapper.properties</br>
├── gradlew</br>
├── gradlew.bat</br>
├── server</br>
│   ├── config-server</br>
│   ├── eureka-server</br>
│   │   ├── .gitignore</br>
│   │   ├── Dockerfile</br>
│   │   ├── build.gradle</br>
│   │   ├── eureka.yaml</br>
│   │   └── src</br>
│   │       └── eurekaserver</br>
│   │           └── EurekaServerApplication.java</br>
│   │   └── resources</br>
│   │       └── application.yaml</br>
│   ├── gateway-server</br>
│   │   ├── .gitignore</br>
│   │   ├── Dockerfile</br>
│   │   ├── build.gradle</br>
│   │   ├── gateway.yaml</br>
│   │   └── src</br>
│   │       └── gatewayserver</br>
│   │           ├── Enum</br>
│   │           │   ├── .DS_Store</br>
│   │           │   ├── CodeEnum.java</br>
│   │           │   ├── ExceptionStatus.java</br>
│   │           │   └── Role.java</br>
│   │           ├── Filter</br>
│   │           │   ├── .DS_Store</br>
│   │           │   ├── GatewayRouter.java</br>
│   │           │   ├── LoginFilter.java</br>
│   │           │   ├── LogoutFilter.java</br>
│   │           │   └── ReissueFilter.java</br>
│   │           ├── GatewayException.java</br>
│   │           ├── GatewayServerApplication.java</br>
│   │           ├── config</br>
│   │           │   ├── .DS_Store</br>
│   │           │   ├── MongoConfig.java</br>
│   │           │   ├── RedisConfig.java</br>
│   │           │   ├── SecurityConfig.java</br>
│   │           │   ├── UriConfiguration.java</br>
│   │           │   ├── UserRoute.java</br>
│   │           │   └── WebClientConfig.java</br>
│   │           ├── controller</br>
│   │           │   └── UserController.java</br>
│   │           ├── jwt</br>
│   │           │   ├── CustomAuthenticationFailureHandler.java</br>
│   │           │   ├── CustomAuthenticationSuccessHandler.java</br>
│   │           │   ├── CustomReactiveAuthenticationManager.java</br>
│   │           │   ├── JWTUtil.java</br>
│   │           │   └── JwtTokenServiceImpl.java</br>
│   │           ├── model</br>
│   │           │   ├── .DS_Store</br>
│   │           │   ├── Domain</br>
│   │           │   │   ├── .DS_Store</br>
│   │           │   │   ├── UserModel.java</br>
│   │           │   │   └── oauth</br>
│   │           │   │       ├── CustomOAuth2User.java</br>
│   │           │   │       ├── CustomUserDetails.java</br>
│   │           │   │       ├── NaverResponse.java</br>
│   │           │   │       └── OAuth2Response.java</br>
│   │           │   ├── LoginModel.java</br>
│   │           │   ├── RegisterModel.java</br>
│   │           │   ├── entity</br>
│   │           │   │   └── User.java</br>
│   │           │   └── repository</br>
│   │           │       └── UserRepository.java</br>
│   │           ├── oauth</br>
│   │           │   ├── CustomOAuth2UserService.java</br>
│   │           │   ├── CustomReactiveUserDetailsService.java</br>
│   │           │   └── CustomSuccessHandler.java</br>
│   │           └── service</br>
│   │               ├── .DS_Store</br>
│   │               ├── Impl</br>
│   │               │   └── UserServiceImpl.java</br>
│   │               └── UserService.java</br>
│   └── secret-server</br>
├── service</br>
│   ├── chat-service</br>
│   │   ├── .gitignore</br>
│   │   ├── Dockerfile</br>
│   │   ├── build.gradle</br>
│   │   ├── chat.yaml</br>
│   │   └── src</br>
│   │       └── chatservice</br>
│   │           ├── ChatServiceApplication.java</br>
│   │           ├── config</br>
│   │           │   ├── ChatMessageRoute.java</br>
│   │           │   ├── ChatRoomRoute.java</br>
│   │           │   ├── ChatUserRoute.java</br>
│   │           │   ├── MongoConfig.java</br>
│   │           │   └── RedisConfig.java</br>
│   │           ├── controller</br>
│   │           │   ├── ChatMessageHandler.java</br>
│   │           │   ├── ChatRoomHandler.java</br>
│   │           │   └── ChatUserHandler.java</br>
│   │           ├── model</br>
│   │           │   ├── domain</br>
│   │           │   │   ├── ChatUnReadUserModel.java</br>
│   │           │   │   ├── ChatUserModel.java</br>
│   │           │   │   ├── message</br>
│   │           │   │   │   ├── ChatMessageModel.java</br>
│   │           │   │   │   └── RequestChatMessageModel.java</br>
│   │           │   │   └── room</br>
│   │           │   │       ├── ChatRoomModel.java</br>
│   │           │   │       ├── ChatRoomNameModel.java</br>
│   │           │   │       └── ChatRoomPasswordModel.java</br>
│   │           │   ├── entity</br>
│   │           │   │   ├── ChatMessage.java</br>
│   │           │   │   ├── ChatRoom.java</br>
│   │           │   │   ├── ChatUser.java</br>
│   │           │   │   └── ChatUserTimeStamp.java</br>
│   │           │   ├── enums</br>
│   │           │   │   └── MessageType.java</br>
│   │           │   └── repository</br>
│   │           │       ├── ChatMessageRepository.java</br>
│   │           │       ├── ChatRoomRepository.java</br>
│   │           │       ├── ChatUserRepository.java</br>
│   │           │       ├── ChatUserTimeStampRepository.java</br>
│   │           │       ├── CustomChatMessageRepository.java</br>
│   │           │       ├── CustomChatRoomRepository.java</br>
│   │           │       ├── CustomChatUserRepository.java</br>
│   │           │       ├── CustomChatUserTimeStampRepository.java</br>
│   │           │       └── impl</br>
│   │           │           ├── CustomChatMessageRepositoryImpl.java</br>
│   │           │           ├── CustomChatRoomRepositoryImpl.java</br>
│   │           │           ├── CustomChatUserRepositoryImpl.java</br>
│   │           │           └── CustomChatUserTimeStampRepositoryImpl.java</br>
│   │           ├── service</br>
│   │           │   ├── ChatService.java</br>
│   │           │   └── impl</br>
│   │           │       └── ChatServiceImpl.java</br>
│   │           └── util</br>
│   │               └── ProfanityFilter.java</br>
│   ├── comment-service</br>
│   │   ├── .gitignore</br>
│   │   ├── Dockerfile</br>
│   │   ├── build.gradle</br>
│   │   ├── comment.yaml</br>
│   │   └── src</br>
│   │       └── commentservice</br>
│   │           ├── CommentServiceApplication.java</br>
│   │           ├── config</br>
│   │           │   └── QuerydslConfig.java</br>
│   │           ├── controller</br>
│   │           │   └── CommentController.java</br>
│   │           ├── model</br>
│   │           │   ├── domain</br>
│   │           │   │   ├── CommentRequestModel.java</br>
│   │           │   │   ├── CommentResponseModel.java</br>
│   │           │   │   ├── ErrorField.java</br>
│   │           │   │   └── ExceptionResponseModel.java</br>
│   │           │   ├── entity</br>
│   │           │   │   └── Comment.java</br>
│   │           │   └── repository</br>
│   │           │       ├── CommentRepository.java</br>
│   │           │       ├── CustomCommentRepository.java</br>
│   │           │       └── impl</br>
│   │           │           └── CommentRepositoryImpl.java</br>
│   │           ├── service</br>
│   │           │   ├── CommentService.java</br>
│   │           │   └── impl</br>
│   │           │       └── CommentServiceImpl.java</br>
│   │           └── util</br>
│   │               └── GlobalExceptionHandler.java</br>
│   ├── file-service</br>
│   │   ├── .gitignore</br>
│   │   ├── Dockerfile</br>
│   │   ├── build.gradle</br>
│   │   ├── file.yaml</br>
│   │   └── src</br>
│   │       └── fileservice</br>
│   │           ├── FileServiceApplication.java</br>
│   │           ├── config</br>
│   │           │   ├── MongoConfig.java</br>
│   │           │   ├── S3Config.java</br>
│   │           │   └── SwaggerConfig.java</br>
│   │           ├── controller</br>
│   │           │   └── FileController.java</br>
│   │           ├── model</br>
│   │           │   ├── domain</br>
│   │           │   │   ├── ErrorField.java</br>
│   │           │   │   ├── ExceptionResponseModel.java</br>
│   │           │   │   ├── FileDeleteModel.java</br>
│   │           │   │   └── FileModel.java</br>
│   │           │   ├── entity</br>
│   │           │   │   └── File.java</br>
│   │           │   ├── enums</br>
│   │           │   │   └── FileType.java</br>
│   │           │   └── repository</br>
│   │           │       ├── FileRepository.java</br>
│   │           │       ├── custom</br>
│   │           │       │   └── FileCustomRepository.java</br>
│   │           │       └── impl</br>
│   │           │           └── FileCustomRepositoryImpl.java</br>
│   │           ├── service</br>
│   │           │   ├── FileService.java</br>
│   │           │   └── impl</br>
│   │           │       └── FileServiceImpl.java</br>
│   │           └── util</br>
│   │               └── GlobalExceptionHandler.java</br>
│   ├── group-service</br>
│   │   ├── .gitignore</br>
│   │   ├── Dockerfile</br>
│   │   ├── build.gradle</br>
│   │   ├── group.yaml</br>
│   │   ├── redis</br>
│   │   │   └── data</br>
│   │   │       └── dump.rdb</br>
│   │   └── src</br>
│   │       └── groupservice</br>
│   │           ├── GroupServiceApplication.java</br>
│   │           ├── config</br>
│   │           │   └── QuerydslConfig.java</br>
│   │           ├── controller</br>
│   │           │   ├── BookController.java</br>
│   │           │   ├── GroupController.java</br>
│   │           │   ├── GroupPostController.java</br>
│   │           │   └── LikeBookController.java</br>
│   │           ├── enums</br>
│   │           │   ├── CodeEnum.java</br>
│   │           │   └── GroupPostCategory.java</br>
│   │           ├── model</br>
│   │           │   ├── domain</br>
│   │           │   │   ├── BookResponseModel.java</br>
│   │           │   │   ├── ErrorField.java</br>
│   │           │   │   ├── ExceptionResponseModel.java</br>
│   │           │   │   ├── GroupModel.java</br>
│   │           │   │   ├── GroupPostModel.java</br>
│   │           │   │   ├── GroupPostResponseModel.java</br>
│   │           │   │   ├── GroupResponseModel.java</br>
│   │           │   │   ├── JoiningModel.java</br>
│   │           │   │   └── LikeBookModel.java</br>
│   │           │   ├── entity</br>
│   │           │   │   ├── Book.java</br>
│   │           │   │   ├── Group.java</br>
│   │           │   │   ├── GroupPost.java</br>
│   │           │   │   ├── Joining.java</br>
│   │           │   │   └── LikeBooks.java</br>
│   ├── book-service</br>
│   │   ├── .gitignore</br>
│   │   ├── Dockerfile</br>
│   │   ├── build.gradle</br>
│   │   ├── book.yaml</br>
│   │   └── src</br>
│   │       │   │           └── bookservice</br>
│   │       │   │               ├── BookServiceApplication.java</br>
│   │       │   │               ├── config</br>
│   │       │   │               │   ├── QuerydslConfig.java</br>
│   │       │   │               │   └── SwaggerConfig.java</br>
│   │       │   │               ├── controller</br>
│   │       │   │               │   ├── BookController.java</br>
│   │       │   │               │   ├── GroupPostController.java</br>
│   │       │   │               │   ├── GroupController.java</br>
│   │       │   │               │   ├── JoiningController.java</br>
│   │       │   │               │   └── LikeBookController.java</br>
│   │       │   │               ├── model</br>
│   │       │   │               │   ├── domain</br>
│   │       │   │               │   │   ├── BookModel.java</br>
│   │       │   │               │   │   ├── GroupPostModel.java</br>
│   │       │   │               │   │   ├── GroupModel.java</br>
│   │       │   │               │   │   ├── JoiningModel.java</br>
│   │       │   │               │   │   ├── LikeBookModel.java</br>
│   │       │   │               │   │   └── ErrorResponseModel.java</br>
│   │       │   │               │   └── dto</br>
│   │       │   │               │       ├── BookDTO.java</br>
│   │       │   │               │       ├── GroupPostDTO.java</br>
│   │       │   │               │       ├── GroupDTO.java</br>
│   │       │   │               │       ├── JoiningDTO.java</br>
│   │       │   │               │       └── LikeBookDTO.java</br>
│   │       │   │               ├── entity</br>
│   │       │   │               │   ├── Book.java</br>
│   │       │   │               │   ├── GroupPost.java</br>
│   │       │   │               │   ├── Group.java</br>
│   │       │   │               │   ├── Joining.java</br>
│   │       │   │               │   └── LikeBook.java</br>
│   │       │   │               ├── repository</br>
│   │       │   │               │   ├── BookRepository.java</br>
│   │       │   │               │   ├── BookRepositoryCustom.java</br>
│   │       │   │               │   ├── GroupPostRepository.java</br>
│   │       │   │               │   ├── GroupPostRepositoryCustom.java</br>
│   │       │   │               │   ├── GroupRepository.java</br>
│   │       │   │               │   ├── GroupRepositoryCustom.java</br>
│   │       │   │               │   ├── JoiningRepository.java</br>
│   │       │   │               │   ├── LikeBooksRepository.java</br>
│   │       │   │               │   ├── LikeBooksRepositoryCustom.java</br>
│   │       │   │               │   └── impl</br>
│   │       │   │               │       ├── BookRepositoryCustomImpl.java</br>
│   │       │   │               │       ├── GroupPostRepositoryCustomImpl.java</br>
│   │       │   │               │       ├── GroupRepositoryCustomImpl.java</br>
│   │       │   │               │       └── LikeBooksRepositoryCustomImpl.java</br>
│   │       │   │               ├── service</br>
│   │       │   │               │   ├── BookService.java</br>
│   │       │   │               │   ├── GroupPostService.java</br>
│   │       │   │               │   ├── GroupService.java</br>
│   │       │   │               │   ├── JoiningService.java</br>
│   │       │   │               │   ├── LikeBookService.java</br>
│   │       │   │               │   └── impl</br>
│   │       │   │               │       ├── BookServiceImpl.java</br>
│   │       │   │               │       ├── GroupPostServiceImpl.java</br>
│   │       │   │               │       ├── GroupServiceImpl.java</br>
│   │       │   │               │       ├── JoiningServiceImpl.java</br>
│   │       │   │               │       └── LikeBookServiceImpl.java</br>
│   │       │   │               └── util</br>
│   │       │   │                   └── GlobalExceptionHandler.java</br>
│   ├── room-service</br>
│   │   ├── .gitignore</br>
│   │   ├── Dockerfile</br>
│   │   ├── build.gradle</br>
│   │   ├── room.yaml</br>
│   │   └── src</br>
│   │       │   │           └── roomservice</br>
│   │       │   │               ├── RoomServiceApplication.java</br>
│   │       │   │               ├── config</br>
│   │       │   │               │   ├── QuerydslConfig.java</br>
│   │       │   │               │   └── SwaggerConfig.java</br>
│   │       │   │               ├── controller</br>
│   │       │   │               │   ├── AccountController.java</br>
│   │       │   │               │   ├── AddressController.java</br>
│   │       │   │               │   ├── BookingController.java</br>
│   │       │   │               │   ├── ReviewController.java</br>
│   │       │   │               │   ├── RoomController.java</br>
│   │       │   │               │   └── TimeController.java</br>
│   │       │   │               ├── model</br>
│   │       │   │               │   ├── domain</br>
│   │       │   │               │   │   ├── AccountCancelModel.java</br>
│   │       │   │               │   │   ├── AccountModel.java</br>
│   │       │   │               │   │   ├── AccountResultModel.java</br>
│   │       │   │               │   │   ├── AddressModel.java</br>
│   │       │   │               │   │   ├── AddressUpdateModel.java</br>
│   │       │   │               │   │   ├── BookingModel.java</br>
│   │       │   │               │   │   ├── ErrorField.java</br>
│   │       │   │               │   │   ├── ExceptionResponseModel.java</br>
│   │       │   │               │   │   ├── ReviewModel.java</br>
│   │       │   │               │   │   ├── ReviewUpdateModel.java</br>
│   │       │   │               │   │   ├── RoomModel.java</br>
│   │       │   │               │   │   ├── RoomUpdateModel.java</br>
│   │       │   │               │   │   ├── RoomWTimeModel.java</br>
│   │       │   │               │   │   ├── TimeModel.java</br>
│   │       │   │               │   │   └── TimeSaveModel.java</br>
│   │       │   │               │   ├── entity</br>
│   │       │   │               │   │   ├── Account.java</br>
│   │       │   │               │   │   ├── Address.java</br>
│   │       │   │               │   │   ├── Booking.java</br>
│   │       │   │               │   │   ├── Review.java</br>
│   │       │   │               │   │   ├── Room.java</br>
│   │       │   │               │   │   └── Time.java</br>
│   │       │   │               │   └── repository</br>
│   │       │   │               │       ├── AccountCustomRepository.java</br>
│   │       │   │               │       ├── AccountRepository.java</br>
│   │       │   │               │       ├── AddressCustomRepository.java</br>
│   │       │   │               │       ├── AddressRepository.java</br>
│   │       │   │               │       ├── BookingCustomRepository.java</br>
│   │       │   │               │       ├── BookingRepository.java</br>
│   │       │   │               │       ├── ReviewCustomRepository.java</br>
│   │       │   │               │       ├── ReviewRepository.java</br>
│   │       │   │               │       ├── RoomCustomRepository.java</br>
│   │       │   │               │       ├── RoomRepository.java</br>
│   │       │   │               │       ├── TimeCustomRepository.java</br>
│   │       │   │               │       ├── TimeRepository.java</br>
│   │       │   │               │       └── impl</br>
│   │       │   │               │           ├── AccountRepositoryImpl.java</br>
│   │       │   │               │           ├── AddressRepositoryImpl.java</br>
│   │       │   │               │           ├── BookingRepositoryImpl.java</br>
│   │       │   │               │           ├── ReviewRepositoryImpl.java</br>
│   │       │   │               │           ├── RoomRepositoryImpl.java</br>
│   │       │   │               │           └── TimeRepositoryImpl.java</br>
│   │       │   │               ├── service</br>
│   │       │   │               │   ├── AccountService.java</br>
│   │       │   │               │   ├── AddressService.java</br>
│   │       │   │               │   ├── BookingService.java</br>
│   │       │   │               │   ├── ReviewService.java</br>
│   │       │   │               │   ├── RoomService.java</br>
│   │       │   │               │   ├── TimeService.java</br>
│   │       │   │               │   └── impl</br>
│   │       │   │               │       ├── AccountServiceImpl.java</br>
│   │       │   │               │       ├── AddressServiceImpl.java</br>
│   │       │   │               │       ├── BookingServiceImpl.java</br>
│   │       │   │               │       ├── ReviewServiceImpl.java</br>
│   │       │   │               │       ├── RoomServiceImpl.java</br>
│   │       │   │               │       └── TimeServiceImpl.java</br>
│   │       │   │               └── util</br>
│   │       │   │                   ├── Converter.java</br>
│   │       │   │                   └── GlobalExceptionHandler.java</br>
│   └── user-service</br>
│       ├── .gitignore</br>
│       ├── Dockerfile</br>
│       ├── build.gradle</br>
│       ├── gradle</br>
│       │   └── wrapper</br>
│       │       ├── gradle-wrapper.jar</br>
│       │       └── gradle-wrapper.properties</br>
│       ├── gradlew</br>
│       ├── gradlew.bat</br>
│       └── src</br>
│           └── userservice</br>
│               ├── UserServiceApplication.java</br>
│               ├── config</br>
│               │   ├── QuerydslConfig.java</br>
│               │   └── SwaggerConfig.java</br>
│               ├── controller</br>
│               │   ├── AdminPostController.java</br>
│               │   ├── DeclarationPostController.java</br>
│               │   ├── FriendController.java</br>
│               │   ├── LikePostController.java</br>
│               │   └── LikeRoomController.java</br>
│               ├── model</br>
│               │   ├── domain</br>
│               │   │   ├── AdminPostModel.java</br>
│               │   │   ├── DeclarationPostModel.java</br>
│               │   │   ├── FriendModel.java</br>
│               │   │   ├── LikePostModel.java</br>
│               │   │   └── LikeRoomModel.java</br>
│               │   ├── entity</br>
│               │   │   ├── AdminPosts.java</br>
│               │   │   ├── DeclarationPosts.java</br>
│               │   │   ├── Friends.java</br>
│               │   │   ├── LikePosts.java</br>
│               │   │   └── LikeRooms.java</br>
│               │   └── repository</br>
│               │       ├── AdminPostRepository.java</br>
│               │       ├── DeclarationPostRepository.java</br>
│               │       ├── FriendRepository.java</br>
│               │       ├── Impl</br>
│               │       │   ├── AdminPostRepositoryImpl.java</br>
│               │       │   ├── DeclarationPostRepositoryImpl.java</br>
│               │       │   ├── FriendRepositoryImpl.java</br>
│               │       │   ├── LikePostRepositoryImpl.java</br>
│               │       │   └── LikeRoomRepositoryImpl.java</br>
│               │       ├── LikePostRepository.java</br>
│               │       ├── LikeRoomRepository.java</br>
│               │       └── custom</br>
│               │           ├── AdminPostRepositoryCustom.java</br>
│               │           ├── DeclarationPostRepositoryCustom.java</br>
│               │           ├── FriendRepositoryCustom.java</br>
│               │           ├── LikePostRepositoryCustom.java</br>
│               │           └── LikeRoomRepositoryCustom.java</br>
│               └── service</br>
│                   ├── AdminPostService.java</br>
│                   ├── DeclarationPostService.java</br>
│                   ├── FriendService.java</br>
│                   ├── LikePostService.java</br>
│                   ├── LikeRoomService.java</br>
│                   └── impl</br>
│                       ├── AdminPostServiceImpl.java</br>
│                       ├── DeclarationPostServiceImpl.java</br>
│                       ├── FriendServiceImpl.java</br>
│                       ├── LikePostServiceImpl.java</br>
│                       └── LikeRoomServiceImpl.java</br>
└── user.yaml</br>
└── settings.gradle</br>
</details>




<h2>📚사용 기술</h2>

&lt;FE&gt;</br>
<img src="https://img.shields.io/badge/nextdotjs-000000?style=for-the-badge&logo=nextdotjs&logoColor=white">
<img src="https://img.shields.io/badge/redux-764ABC?style=for-the-badge&logo=redux&logoColor=white">
<img src="https://img.shields.io/badge/yarn-2C8EBB?style=for-the-badge&logo=yarn&logoColor=white"></br>

&lt;BE&gt;</br>
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/netflix-E50914?style=for-the-badge&logo=netflix&logoColor=white"></br>

&lt;DB&gt;</br>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/mongodb-47A248?style=for-the-badge&logo=mongodb&logoColor=white"></br>

&lt;CI/CD&gt;</br>
<img src="https://img.shields.io/badge/navercloud-03C75A?style=for-the-badge&logo=naver&logoColor=white">
<img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white">
<img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white"></br>

&lt;OTHER&gt;</br>
<img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"></br>

<h2>📚시스템 아키텍처</h2>

![파란만장 아키텍쳐](https://github.com/user-attachments/assets/c4ed55a7-f752-4dbe-9c69-cae1bbc30a6f)

<!--나중에 지피티로 돌려서 좀 예쁘게 수정....-->
<h2>📚개발기간</h2>
2024.08.21.~2024.10.

<h2>📚프로젝트 산출물</h2>

<ul style="list-style-type:circle;">
 <li>
  
  [api 명세서](https://www.notion.so/REST-API-11e4a940b48480d5ac99ef3f726bc0c3)
 </li>
 <li>
  
  [erd](https://www.notion.so/ERD-11e4a940b4848071a262d0581befa07f)</li>
 <li>
  
  [개발환경](https://www.notion.so/11e4a940b48480688ac3fcce8025d49b)</li>
 <li>
     
  [발표자료](https://github.com/paranmanzang/paran_msa/blob/master/paranmanzang%20final_rev1.pptx)</li>
 <li>
  
  [발표영상](https://www.youtube.com/)</li>
</ul>

