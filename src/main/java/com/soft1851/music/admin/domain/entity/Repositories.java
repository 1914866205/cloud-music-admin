package com.soft1851.music.admin.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/5/7 12:19
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Repositories {
    private String id;
    private String nodeId;
    private String name;
    private String fullName;
    private boolean privated;
    private GithubUser owner;
    private String htmlUrl;
    private String description;
    private boolean fork;
    private String url;
    private String forksUrl;
    private String keysUrl;
    private String collaboratorsUrl;
    private String teamsUrl;
    private String hooksUrl;
    private String issueEventsUrl;
    private String eventsUrl;
    private String assigneesUrl;
    private String branchesUrl;
    private String tagsUrl;
    private String blobsUrl;
    private String gitTagsUrl;
    private String gitRefsUrl;
    private String treesUrl;
    private String statusesUrl;
    private String languagesUrl;
    private String stargazersUrl;
    private String contributorsUrl;
    private String subscribersUrl;
    private String subscriptionUrl;
    private String commitsUrl;
    private String gitCommitsUrl;
    private String commentsUrl;
    private String issueCommentUrl;
    private String contentUrl;
    private String compareUrl;
    private String mergesUrl;
    private String archiveUrl;
    private String downloadsUrl;
    private String issuesUrl;
    private String pullsUrl;
    private String milestonesUrl;
    private String notificationsUrl;
    private String labelsUrl;
    private String releasesUrl;
    private String deploymentsUrl;
    private String createdAt;
    private String updatedAt;
    private String pushedAt;
    private String gitUrl;
    private String sshUrl;
    private String cloneUrl;
    private String svnUrl;
    private String homepage;
    private int size;
    private int stargazersCount;
    private int watchersCount;
    private String language;
    private boolean hasIssues;
    private boolean hasProjects;
    private boolean hasDownloads;
    private boolean hasWiki;
    private boolean hasPages;
    private int forksCount;
    private String mirrorUrl;
    private boolean archived;
    private boolean disabled;
    private int openIssuesCount;
    private String license;
    private int forks;
    private int openIssues;
    private int watchers;
    private String defaultBranch;
    private Permissions permissions;
}
