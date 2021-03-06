/*
 * Copyright (c) 2013, 2015 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.nodes.core;

import com.oracle.truffle.api.dsl.ImportGuards;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;

import org.jruby.truffle.nodes.RubyNode;
import org.jruby.truffle.runtime.RubyContext;
import org.jruby.truffle.runtime.core.RubyArray;
import org.jruby.truffle.runtime.util.ArrayUtils;

import java.util.Arrays;

@NodeChildren({@NodeChild(value = "array", type = RubyNode.class)})
@ImportGuards(ArrayGuards.class)
public abstract class ArraySliceNode extends RubyNode {

    final int from; // positive
    final int to; // negative, exclusive

    public ArraySliceNode(RubyContext context, SourceSection sourceSection, int from, int to) {
        super(context, sourceSection);
        assert from >= 0;
        assert to <= 0;
        this.from = from;
        this.to = to;
    }

    public ArraySliceNode(ArraySliceNode prev) {
        super(prev);
        from = prev.from;
        to = prev.to;
    }

    @Specialization(guards = "isNull")
    public RubyArray sliceNull(RubyArray array) {
        notDesignedForCompilation();

        return new RubyArray(getContext().getCoreLibrary().getArrayClass());
    }

    @Specialization(guards = "isIntegerFixnum")
    public RubyArray sliceIntegerFixnum(RubyArray array) {
        notDesignedForCompilation();
        final int to = array.getSize() + this.to;

        if (from >= to) {
            return new RubyArray(getContext().getCoreLibrary().getArrayClass());
        } else {
            return new RubyArray(getContext().getCoreLibrary().getArrayClass(), ArrayUtils.extractRange((int[]) array.getStore(), from, to), to - from);
        }
    }

    @Specialization(guards = "isLongFixnum")
    public RubyArray sliceLongFixnum(RubyArray array) {
        notDesignedForCompilation();
        final int to = array.getSize() + this.to;

        if (from >= to) {
            return new RubyArray(getContext().getCoreLibrary().getArrayClass());
        } else {
            return new RubyArray(getContext().getCoreLibrary().getArrayClass(), ArrayUtils.extractRange((long[]) array.getStore(), from, to), to - from);
        }
    }

    @Specialization(guards = "isFloat")
    public RubyArray sliceFloat(RubyArray array) {
        notDesignedForCompilation();
        final int to = array.getSize() + this.to;

        if (from >= to) {
            return new RubyArray(getContext().getCoreLibrary().getArrayClass());
        } else {
            return new RubyArray(getContext().getCoreLibrary().getArrayClass(), ArrayUtils.extractRange((double[]) array.getStore(), from, to), to - from);
        }
    }

    @Specialization(guards = "isObject")
    public RubyArray sliceObject(RubyArray array) {
        notDesignedForCompilation();
        final int to = array.getSize() + this.to;

        if (from >= to) {
            return new RubyArray(getContext().getCoreLibrary().getArrayClass());
        } else {
            return new RubyArray(getContext().getCoreLibrary().getArrayClass(), ArrayUtils.extractRange((Object[]) array.getStore(), from, to), to - from);
        }
    }

}
