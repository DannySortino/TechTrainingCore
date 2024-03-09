package org.training.core.framework;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class GitRepositoryInfoTest {

  private GitRepositoryInfo gitRepositoryInfo;

  @Before
  public void setUp() throws IOException {
    gitRepositoryInfo = new GitRepositoryInfo();
  }

  @After
  public void tearDown() {
    gitRepositoryInfo.close();
  }

  @Test
  public void testGetCurrentBranch() {
    String branch = gitRepositoryInfo.getCurrentBranch();
    assertNotNull(branch);
  }
//
//  @Test
//  public void testGetGitHistory() {
//    List<String> history = gitRepositoryInfo.getGitHistory();
//    assertNotNull(history);
//    assertFalse(history.isEmpty());
//  }
//
//  @Test
//  public void testHasMerges() {
//    boolean hasMerges = gitRepositoryInfo.hasMerges();
//    assertTrue(hasMerges || !hasMerges);
//  }

  @Test
  public void testOpenWithRepoPath() {
    String repoPath = System.getProperty("user.dir");
    GitRepositoryInfo repoInfo = new GitRepositoryInfo(repoPath);
    assertNotNull(repoInfo);
    repoInfo.close();
  }
}
