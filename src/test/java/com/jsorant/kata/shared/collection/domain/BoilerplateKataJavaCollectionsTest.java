package com.jsorant.kata.shared.collection.domain;

import static org.assertj.core.api.Assertions.*;

import com.jsorant.kata.UnitTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class BoilerplateKataJavaCollectionsTest {

  @Nested
  @DisplayName("Collections")
  class BoilerplateKataJavaCollectionsCollectionsTest {

    @Test
    void shouldGetEmptyImmutableCollectionFromNullCollection() {
      Collection<Object> input = null;
      Collection<Object> collection = BoilerplateKataJavaCollections.immutable(input);

      assertThat(collection).isEmpty();
      assertThatThrownBy(collection::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldGetImmutableCollectionFromMutableCollection() {
      Collection<String> input = new ArrayList<>();
      input.add("value");
      Collection<String> collection = BoilerplateKataJavaCollections.immutable(input);

      assertThat(collection).containsExactly("value");
      assertThatThrownBy(collection::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }
  }

  @Nested
  @DisplayName("Set")
  class BoilerplateKataJavaCollectionsSetTest {

    @Test
    void shouldGetEmptyImmutableCollectionFromNullCollection() {
      Set<Object> input = null;
      Set<Object> set = BoilerplateKataJavaCollections.immutable(input);

      assertThat(set).isEmpty();
      assertThatThrownBy(set::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldGetImmutableCollectionFromMutableCollection() {
      Set<String> input = new HashSet<>();
      input.add("value");
      Set<String> set = BoilerplateKataJavaCollections.immutable(input);

      assertThat(set).containsExactly("value");
      assertThatThrownBy(set::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }
  }

  @Nested
  @DisplayName("List")
  class BoilerplateKataJavaCollectionsListTest {

    @Test
    void shouldGetEmptyImmutableCollectionFromNullCollection() {
      List<Object> input = null;
      List<Object> list = BoilerplateKataJavaCollections.immutable(input);

      assertThat(list).isEmpty();
      assertThatThrownBy(list::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldGetImmutableCollectionFromMutableCollection() {
      List<String> input = new ArrayList<>();
      input.add("value");
      List<String> list = BoilerplateKataJavaCollections.immutable(input);

      assertThat(list).containsExactly("value");
      assertThatThrownBy(list::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }
  }

  @Nested
  @DisplayName("Map")
  class BoilerplateKataJavaMapTest {

    @Test
    void shouldGetEmptyImmutableMapFromNullMap() {
      Map<Object, Object> input = null;
      Map<Object, Object> map = BoilerplateKataJavaCollections.immutable(input);

      assertThat(map).isEmpty();
      assertThatThrownBy(map::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldGetImmutableMapFromMutableMap() {
      Map<String, String> input = new HashMap<>();
      input.put("key", "value");
      Map<String, String> map = BoilerplateKataJavaCollections.immutable(input);

      assertThat(map).containsExactly(Map.entry("key", "value"));
      assertThatThrownBy(map::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }
  }
}
