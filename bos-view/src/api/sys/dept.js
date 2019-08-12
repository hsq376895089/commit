import request from '@/utils/request'


export function groupDept() {
  return request({
    url: '/dept/groupDept',
    method: 'get'
  })
}

export function add(dept) {
  return request({
    url: '/dept/addDept',
    method: 'post',
    params: dept
  })
}

export function deleteDept(id) {
  console.debug('删除')
  console.debug(id)
  return request({
    url: '/dept/deleteDeptById ',
    method: 'post',
    data: {id}
  })
}

export function update(dept) {
  return request({
    url: '/dept/updateDeptById',
    method: 'post',
    params: dept
  })
}

export function list(query) {
  return request({
    url: '/dept/deptAll',
    method: 'get',
    params: query
  })
}

export function selectByDeptname(dname) {
  return request({
    url:'/dept/selectByDeptname',
    method:'post',
    data:{dname}
  })
}

export function selectById(id) {
  return request({
    url:'/dept/selectById',
    method:'post',
    data:{id}
  })
}
