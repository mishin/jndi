/*
 *  Copyright (c) 2002-2017, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */package com.manorrock.jndi;

import java.util.NoSuchElementException;
import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NotContextException;
import javax.naming.OperationNotSupportedException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The JUnit tests for the SparrowInitialContext class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class DefaultInitialContextTest {

    /**
     * Test bind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testBind() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Name name = new CompositeName("name");
        context.bind(name, "value");
        assertNotNull(context.lookup(name));
    }

    /**
     * Test bind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testBind2() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.createSubcontext("context");
        context.bind("context/name1", "value1");
        assertNotNull(context.lookup("context/name1"));
        assertEquals("value1", context.lookup("context/name1"));
        context.bind("context/name2", "value2");
        assertNotNull(context.lookup("context/name2"));
        assertEquals("value2", context.lookup("context/name2"));
    }

    /**
     * Test bind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameAlreadyBoundException.class)
    public void testBind3() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Name name = new CompositeName("name");
        context.bind(name, "value");
        assertNotNull(context.lookup(name));
        context.bind(name, "value");
    }

    /**
     * Test close method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testClose() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.close();
    }

    /**
     * Test rename method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NamingException.class)
    public void testClose2() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("name", "value");
        context.close();
        context.rename("rename", "name");
    }

    /**
     * Test createSubcontext method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testCreateSubcontext() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.createSubcontext(new CompositeName("context"));
        context.bind("context/name1", "value1");
        assertNotNull(context.lookup("context/name1"));
    }

    /**
     * Test composeName method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testComposeName() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        assertNotNull(context.composeName("name", ""));
    }

    /**
     * Test composeName method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NamingException.class)
    public void testComposeName2() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        assertNotNull(context.composeName("name", "kaboom"));
    }

    /**
     * Test destroySubcontext method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testDestroySubcontext() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.createSubcontext(new CompositeName("context"));
        context.destroySubcontext("context");
    }

    /**
     * Test destroySubcontext method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = ContextNotEmptyException.class)
    public void testDestroySubcontext2() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.createSubcontext(new CompositeName("context"));
        context.bind("context/name", 12);
        context.destroySubcontext("context");
    }

    /**
     * Test destroySubcontext method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NotContextException.class)
    public void testDestroySubcontext3() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("name", 12);
        context.destroySubcontext("name");
    }

    /**
     * Test destroySubcontext method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testDestroySubcontext4() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.destroySubcontext("name");
    }

    /**
     * Test destroySubcontext method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testDestroySubcontext5() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("context1/context2/name", 12);
        context.unbind("context1/context2/name");
        context.destroySubcontext(new CompositeName("context1/context2"));
    }

    /**
     * Test destroySubcontext method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testDestroySubcontext6() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("context1/name", 12);
        context.unbind("context1/name");
        context.destroySubcontext(new CompositeName("context1/context2"));
    }

    /**
     * Test getEnvironment method.
     *
     * @throws Exception when a serious error occurs.
     */
    @Test
    public void testGetEnvironment() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        assertNotNull(context.getEnvironment());
    }

    /**
     * Test getNameInNamespace method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = OperationNotSupportedException.class)
    public void testGetNameInNamespace() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.getNameInNamespace();
    }

    /**
     * Test lookup method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testLookup() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Name name = new CompositeName("name");
        context.lookup(name);
    }

    /**
     * Test list method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testList() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Name name = new CompositeName("name");
        NamingEnumeration<NameClassPair> enumeration = context.list(name);
        assertNotNull(enumeration);
    }

    /**
     * Test listBindings method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testListBindings() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.createSubcontext("name");
        NamingEnumeration<Binding> enumeration = context.listBindings("name");
        assertNotNull(enumeration);
    }

    /**
     * Test listBindings method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NoSuchElementException.class)
    public void testListBindings2() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Context subContext = context.createSubcontext("subcontext");
        subContext.bind("name", "value");
        NamingEnumeration<Binding> enumeration = context.listBindings("subcontext");
        assertNotNull(enumeration);
        assertTrue(enumeration.hasMore());
        Binding binding = enumeration.next();
        assertEquals("name", binding.getName());
        enumeration.next();
    }

    /**
     * Test listBindings method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NamingException.class)
    public void testListBindings3() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        NamingEnumeration<Binding> enumeration = context.listBindings("name");
        assertNotNull(enumeration);
        enumeration.close();
        enumeration.hasMore();
    }

    /**
     * Test listBindings method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testListBindings4() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Context subContext = context.createSubcontext("subcontext");
        subContext.bind("name", "value");
        NamingEnumeration<Binding> enumeration = context.listBindings("subcontext");
        assertNotNull(enumeration);
        assertTrue(enumeration.hasMoreElements());
        Binding binding = enumeration.nextElement();
        assertEquals("name", binding.getName());
    }

    /**
     * Test listBindings method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testListBindings5() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Context subContext = context.createSubcontext("context1");
        subContext.createSubcontext("context2");
        NamingEnumeration<Binding> enumeration = context.listBindings("context1/context2");
        assertNotNull(enumeration);
    }

    /**
     * Test listBindings method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NamingException.class)
    public void testListBindings6() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Context subContext = context.createSubcontext("context1");
        subContext.bind("context2", "value");
        context.listBindings("context1/context2");
    }

    /**
     * Test listBindings method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testListBindings7() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.createSubcontext("name");
        NamingEnumeration<Binding> enumeration = context.listBindings(new CompositeName("name"));
        assertNotNull(enumeration);
    }
    
    /**
     * Test lookup method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testLookup2() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.lookup("name");
    }

    /**
     * Test lookup method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testLookup3() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("subContext/name", true);
        assertNotNull(context.lookup("subContext/name"));
    }

    /**
     * Test lookup method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testLookup4() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("subContext/name", true);
        context.lookup("subContext/subContext2/name");
    }

    /**
     * Test rebind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testRebind() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Name name = new CompositeName("name");
        context.rebind(name, "value");
        assertNotNull(context.lookup(name));
    }

    /**
     * Test rebind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testRebind2() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Name name = new CompositeName("context/name");
        context.rebind(name, "value");
        assertNotNull(context.lookup(name));
        context.rebind(name, "value");
        assertNotNull(context.lookup(name));
    }

    /**
     * Test rename method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameAlreadyBoundException.class)
    public void testRename() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("name", "value");
        context.rename("rename", "name");
    }

    /**
     * Test rename method.
     *
     * @throws Exception when an error occurs.
     */
    @Test
    public void testRename2() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("name", "value");
        context.rename(new CompositeName("name"), new CompositeName("newname"));
    }

    /**
     * Test unbind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testUnbind() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("name", "value");
        assertNotNull(context.lookup("name"));
        context.unbind("name");
        context.lookup("name");
    }

    /**
     * Test unbind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testUnbind2() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        Name name = new CompositeName("name");
        context.bind(name, "value");
        assertNotNull(context.lookup(name));
        context.unbind(name);
        context.lookup(name);
    }

    /**
     * Test unbind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testUnbind3() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.unbind("composite/name");
    }

    /**
     * Test unbind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testUnbind4() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("composite/name", "test");
        context.unbind("composite/name");
        context.lookup("composite/name");
    }

    /**
     * Test unbind method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = NameNotFoundException.class)
    public void testUnbind5() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.bind("composite/name", "test");
        context.unbind("composite2/name");
    }

    /**
     * Test lookupLink method.
     *
     * @throws Exception when an error occurs.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testLookupLink() throws Exception {
        DefaultInitialContext context = new DefaultInitialContext();
        context.lookupLink(new CompositeName());
    }
}
