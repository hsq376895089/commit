import request from '@/utils/request'

export function add(role) {
  return request({
    url: '/role/addRole',
    method: 'post',
    params: role
  })
}

export function deleteRole(id) {
  console.debug(id)
  return request({
    url: '/role/deleteRoleById ',
    method: 'post',
    data: { id }
  })
}

export function update(role) {
  return request({
    url: '/role/updateRoleById',
    method: 'post',
    params: role
  })
}

export function list(query) {
  return request({
    url: '/role/roleAll',
    method: 'get',
    params: query
  })
}

export function selectByName(rname) {
  return request({
    url: '/role/selectByName',
    method: 'post',
    data: {rname}
  })
}

