import axios from 'axios';

const client = axios.create({
  baseURL: '/api'
});

export const fetchPosts = (keyword) =>
  client.get('/posts', { params: { keyword } }).then((res) => res.data);

export const fetchPost = (id) =>
  client.get(`/posts/${id}`).then((res) => res.data);

export const createPost = (payload) =>
  client.post('/posts', payload).then((res) => res.data);

export const updatePost = (id, payload) =>
  client.put(`/posts/${id}`, payload).then((res) => res.data);

export const deletePost = (id) => client.delete(`/posts/${id}`);

export const fetchCategories = () =>
  client.get('/categories').then((res) => res.data);

export const createCategory = (payload) =>
  client.post('/categories', payload).then((res) => res.data);
