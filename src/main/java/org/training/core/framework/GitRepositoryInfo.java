package org.training.core.framework;

import java.util.Collection;
import java.util.stream.StreamSupport;
import lombok.SneakyThrows;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.util.List;

public class GitRepositoryInfo implements AutoCloseable{

  private final Repository repository;
  private final Git git;

  @SneakyThrows
  public GitRepositoryInfo() {
    git = Git.open(new File(System.getProperty("user.dir")));
    repository = git.getRepository();
  }

  @SneakyThrows
  public GitRepositoryInfo(String repoPath) {
    git = Git.open(new File(repoPath));
    repository = git.getRepository();
  }

  @SneakyThrows
  public String getCurrentBranch() {
    return repository.getBranch();
  }

  @SneakyThrows
  public List<String> getGitHistory() {
    Iterable<RevCommit> commits = git.log().call();
    return StreamSupport.stream(commits.spliterator(), false)
        .map(commit -> commit.getId().getName() + ": " + commit.getShortMessage())
        .toList();
  }

  @SneakyThrows
  public List<RevCommit> getStashes() {
    Collection<RevCommit> stashes = git.stashList().call();
    return List.copyOf(stashes);
  }

  public boolean hasStashes() {
    return !getStashes().isEmpty();
  }

  @SneakyThrows
  public boolean hasMerges() {
    Iterable<RevCommit> commits = git.log().call();
    return StreamSupport.stream(commits.spliterator(), false)
        .anyMatch(commit -> commit.getParentCount() > 1);
  }

  public void close() {
    repository.close();
  }
}
