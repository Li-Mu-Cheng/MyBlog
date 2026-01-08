<template>
  <div class="app">
    <header class="header">
      <h1>个人博客</h1>
      <p>Spring Boot + MyBatis + MySQL + Redis + Vue</p>
    </header>

    <section class="panel">
      <h2>新增分类</h2>
      <form class="form" @submit.prevent="handleCreateCategory">
        <input v-model="newCategory.name" placeholder="分类名称" />
        <input v-model="newCategory.description" placeholder="描述" />
        <button type="submit">创建分类</button>
      </form>
    </section>

    <section class="panel">
      <h2>新增文章</h2>
      <form class="form" @submit.prevent="handleCreatePost">
        <select v-model="newPost.categoryId">
          <option disabled value="">选择分类</option>
          <option v-for="category in categories" :key="category.id" :value="category.id">
            {{ category.name }}
          </option>
        </select>
        <input v-model="newPost.title" placeholder="标题" />
        <input v-model="newPost.summary" placeholder="摘要" />
        <textarea v-model="newPost.content" placeholder="正文"></textarea>
        <button type="submit">发布文章</button>
      </form>
    </section>

    <section class="panel">
      <div class="toolbar">
        <h2>文章列表</h2>
        <input v-model="keyword" placeholder="搜索标题/摘要" @input="loadPosts" />
      </div>
      <ul class="post-list">
        <li v-for="post in posts" :key="post.id" @click="selectPost(post)">
          <h3>{{ post.title }}</h3>
          <p>{{ post.summary }}</p>
          <small>{{ post.status }}</small>
        </li>
      </ul>
    </section>

    <section v-if="selectedPost" class="panel">
      <h2>文章详情</h2>
      <div class="detail">
        <h3>{{ selectedPost.title }}</h3>
        <p>{{ selectedPost.content }}</p>
      </div>
      <div class="actions">
        <button @click="handleDeletePost">删除文章</button>
      </div>
    </section>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import {
  createCategory,
  createPost,
  deletePost,
  fetchCategories,
  fetchPost,
  fetchPosts
} from './api';

const posts = ref([]);
const categories = ref([]);
const selectedPost = ref(null);
const keyword = ref('');

const newCategory = reactive({
  name: '',
  description: ''
});

const newPost = reactive({
  categoryId: '',
  title: '',
  summary: '',
  content: '',
  status: 'PUBLISHED'
});

const loadCategories = async () => {
  categories.value = await fetchCategories();
};

const loadPosts = async () => {
  posts.value = await fetchPosts(keyword.value);
};

const selectPost = async (post) => {
  selectedPost.value = await fetchPost(post.id);
};

const handleCreateCategory = async () => {
  if (!newCategory.name) return;
  await createCategory({ ...newCategory });
  newCategory.name = '';
  newCategory.description = '';
  await loadCategories();
};

const handleCreatePost = async () => {
  if (!newPost.title || !newPost.content) return;
  await createPost({ ...newPost, categoryId: newPost.categoryId || null });
  newPost.title = '';
  newPost.summary = '';
  newPost.content = '';
  await loadPosts();
};

const handleDeletePost = async () => {
  if (!selectedPost.value) return;
  await deletePost(selectedPost.value.id);
  selectedPost.value = null;
  await loadPosts();
};

onMounted(async () => {
  await Promise.all([loadCategories(), loadPosts()]);
});
</script>

<style scoped>
.app {
  font-family: "Noto Sans SC", sans-serif;
  margin: 0 auto;
  max-width: 900px;
  padding: 32px 20px 80px;
  color: #1f2937;
}

.header {
  margin-bottom: 24px;
  border-bottom: 1px solid #e5e7eb;
  padding-bottom: 16px;
}

.panel {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(15, 23, 42, 0.05);
}

.form {
  display: grid;
  gap: 12px;
}

input,
select,
textarea {
  padding: 10px 12px;
  border-radius: 8px;
  border: 1px solid #d1d5db;
  font-size: 14px;
}

textarea {
  min-height: 120px;
  resize: vertical;
}

button {
  background: #2563eb;
  color: #fff;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
}

button:hover {
  background: #1d4ed8;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
}

.post-list {
  list-style: none;
  padding: 0;
  margin: 16px 0 0;
  display: grid;
  gap: 12px;
}

.post-list li {
  padding: 12px;
  border-radius: 10px;
  border: 1px solid #e5e7eb;
  cursor: pointer;
}

.detail {
  background: #f9fafb;
  padding: 16px;
  border-radius: 10px;
}

.actions {
  margin-top: 12px;
}
</style>
