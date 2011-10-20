package uk.ac.ebi.interpro.scan.web.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.interpro.scan.web.io.EntryHierarchy;

import javax.annotation.Resource;

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * Tests for {@link uk.ac.ebi.interpro.scan.web.io.EntryHierarchy}
 *
 * @author  Matthew Fraser
 * @version $Id$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EntryHierarchyTest {

    @Resource
    private EntryHierarchy entryHierarchy;

    @Test
    public void testEntryColours() {
        Map<String, Integer> entryColoursMap = entryHierarchy.getEntryColourMap();
        assertNotNull(entryColoursMap);
        assertEquals(2, entryColoursMap.size());
        assertFalse(entryColoursMap.containsKey("invalid"));

        assertEquals(45, entryHierarchy.getEntryColour("IPR011987"));
        assertEquals(44, entryHierarchy.getEntryColour("IPR011986"));
        assertEquals(-1, entryHierarchy.getEntryColour("invalid"));
        assertEquals(-1, entryHierarchy.getEntryColour("doesnotexist"));
    }
}
