local function get_list(key)
    local values = redis.call('LRANGE', key, 0, -1) -- 获取整个列表
    return values
end
return get_list(KEYS[1])
