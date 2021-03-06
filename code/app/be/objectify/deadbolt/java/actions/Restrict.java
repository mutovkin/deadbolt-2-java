/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.objectify.deadbolt.java.actions;

import be.objectify.deadbolt.java.DeadboltHandler;
import play.mvc.With;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Within an {@link Group} roles are ANDed, and between {@link Group} the role groups are ORed.  For example,
 * @Restrict({@Group("foo"), @Group("hurdy", "gurdy)}) means the {@link be.objectify.deadbolt.core.models.Subject} must have either the
 * foo role OR both the hurdy AND gurdy roles.
 * <p/>
 * Role names that start with ! are negated, so @Restrict({@Group("foo", "bar"), @Group("hurdy", "!gurdy")}) requires
 * the {@link be.objectify.deadbolt.core.models.Subject} to have either the foo role AND the bar roles, or the hurdy AND NOT the gurdy
 * roles.

 * @author Steve Chaloner (steve@objectify.be)
 */
@With(RestrictAction.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
public @interface Restrict
{
    /**
     * Within an {@link Group}, the relation is AND.  Between {@link Group}s, the relationship is OR.
     *
     * @return
     */
    Group[] value();

    /**
     * Indicates the expected response type.  Useful when working with non-HTML responses.  This is free text, which you
     * can use in {@link be.objectify.deadbolt.java.DeadboltHandler#onAuthFailure} to decide on how to handle the response.
     *
     * @return a content indicator
     */
    String content() default "";

    /**
     * Use a specific {@link be.objectify.deadbolt.java.DeadboltHandler} for this restriction in place of the global one.
     *
     * @return the class of the DeadboltHandler you want to use
     * @deprecated Use {@link Restrict#handlerKey()} instead
     */
    @Deprecated
    Class<? extends DeadboltHandler> handler() default DeadboltHandler.class;

    /**
     * Use a specific {@link be.objectify.deadbolt.java.DeadboltHandler} for this restriction in place of the global
     * one, identified by a key.
     *
     * @return the ky of the handler
     */
    String handlerKey() default "";

    /**
     * If true, the annotation will only be run if there is a {@link DeferredDeadbolt} annotation at the class level.
     *
     * @return true iff the associated action should be deferred until class-level annotations are applied.
     */
    boolean deferred() default false;
}