import request from '@/utils/request'

export function list(query) {
  return request({
    url: 'menu/menuAll',
    method: 'get',
    params: query
  })
}

export function add(menu) {
  return request({
    url: 'menu/addMenu',
    method: 'get',
    params: menu
  })
}

export function update(menu) {
  return request({
    url: 'menu/updateMenuById',
    method: 'post',
    params: menu
   })
 }

export function deleteMenu(id) {
  return request({
    url: 'menu/deleteMenuById',
    method: 'post',
    data: {id}
  })
}

export function selectByMenuname(mname) {
  return request({
    url: 'menu/selectByMenuname',
    method: 'post',
    data: {mname}
  })
}
