package com.github.chrishantha.jfr.flamegraph.output;

import com.beust.jcommander.IStringConverter;

/**
 * Different types of events possibly available in a JFR recording.
 */
public enum EventType {

    EVENT_METHOD_PROFILING_SAMPLE("vm/prof/execution_sample", "cpu"),
    EVENT_ALLOCATION_IN_NEW_TLAB("java/object_alloc_in_new_TLAB", "allocation-tlab", true),
    EVENT_ALLOCATION_OUTSIDE_TLAB("java/object_alloc_outside_TLAB", "allocation-outside-tlab", true),
    EVENT_JAVA_EXCEPTION("java/statistics/throwables", "exceptions"),
    EVENT_JAVA_MONITOR_BLOCKED("java/monitor_wait", "monitor-blocked");

    /**
     * Path as declared in .jfc files
     */
    private final String path;

    /**
     * Id used as a command line option
     */
    private final String id;

    /**
     * True if the event is allocation-related
     */
    private final boolean isAllocation;

    EventType(String path, String id, boolean isAllocation) {
        this.path = path;
        this.id = id;
        this.isAllocation = isAllocation;
    }

    EventType(String path, String id) {
        this(path, id, false);
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return id;
    }

    /**
     * @return true if the event is allocated related
     */
    public boolean isAllocation() {
        return isAllocation;
    }

    public static EventType from(String path) {
        for (EventType eventType : values()) {
            if (eventType.id.equals(path)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Event type [" + path + "] does not exist.");
    }

    public static final class EventTypeConverter implements IStringConverter<EventType> {
        @Override
        public EventType convert(String value) {
            return EventType.from(value);
        }
    }
}
