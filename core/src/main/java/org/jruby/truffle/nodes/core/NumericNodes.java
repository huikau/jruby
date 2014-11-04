/*
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.nodes.core;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import org.jruby.truffle.runtime.RubyContext;

import java.math.BigInteger;

@CoreClass(name = "Numeric")
public abstract class NumericNodes {

    @CoreMethod(names = "+@")
    public abstract static class PosNode extends CoreMethodNode {

        public PosNode(RubyContext context, SourceSection sourceSection) {
            super(context, sourceSection);
        }

        public PosNode(PosNode prev) {
            super(prev);
        }

        @Specialization
        public int pos(int value) {
            return value;
        }

        @Specialization
        public long pos(long value) {
            return value;
        }

        @Specialization
        public BigInteger pos(BigInteger value) {
            return value;
        }

        @Specialization
        public double pos(double value) {
            return value;
        }

    }

    @CoreMethod(names = "nonzero?")
    public abstract static class NonZeroNode extends CoreMethodNode {

        public NonZeroNode(RubyContext context, SourceSection sourceSection) {
            super(context, sourceSection);
        }

        public NonZeroNode(NonZeroNode prev) {
            super(prev);
        }

        @Specialization
        public Object nonZero(int value) {
            if (value == 0) {
                return false;
            } else {
                return value;
            }
        }

        @Specialization
        public Object nonZero(long value) {
            if (value == 0L) {
                return false;
            } else {
                return value;
            }
        }

        @Specialization
        public Object nonZero(BigInteger value) {
            if (value.equals(BigInteger.ZERO)) {
                return false;
            } else {
                return value;
            }
        }

        @Specialization
        public Object nonZero(double value) {
            if (value == 0.0) {
                return false;
            } else {
                return value;
            }
        }

    }

}