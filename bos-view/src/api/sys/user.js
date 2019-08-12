import request from '@/utils/request'

export function add(user) {
  return request({
    url: '/user/addUser',
    method: 'post',
    params: user
  })
}

export function deleteUser(id) {
  console.debug(id)
  return request({
    url: '/user/deleteUserById ',
    method: 'post',
    data: { id }
  })
}

export function update(user) {
  return request({
    url: '/user/updateUserById',
    method: 'post',
    params: user
  })
}

export function list(query) {
  return request({
    url: '/user/userAll',
    method: 'get',
    params: query
  })
}

export function updateArticle(data) {
  return request({
    url: '/user/updateArticle',
    method: 'post',
    params: data
  })
}

export function selectByUsername(name) {
  return request({
    url:'/user/selectByUsername',
    method:'post',
    data:{name}
  })
}
