package com.ssafy.trip.sse.model;
//package com.ssafy.trip.notification.model;
//
//@Getter
//@Entity
//@NoArgsConstructor
//public class Notification extends Timestamped {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Embedded
//    private NotificationContent content;
//
//    @Embedded
//    private RelatedUrl url;
//
//    @Column(nullable = false)
//    private Boolean isRead;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private NotificationType notificationType;
//
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MEMBER_ID")
//    private Member receiver;
//
//    @Builder
//    public Notification(Member receiver, NotificationType notificationType, String content, String url) {
//        this.receiver = receiver;
//        this.notificationType = notificationType;
//        this.content = new NotificationContent(content);
//        this.url = new RelatedUrl(url);
//        this.isRead = false;
//    }
//
//    public String getContent() {
//        return content.getContent();
//    }
//
//    public String getUrl() {
//        return url.getUrl();
//    }
//
//    public void read(){
//        isRead = true;
//    }
//}
