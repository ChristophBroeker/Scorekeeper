package com.github.scorekeeper.persistence;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration("/persistence-context.xml")
public abstract class AbstractScorekeeperPersistenceTest extends AbstractTransactionalJUnit4SpringContextTests {

}
