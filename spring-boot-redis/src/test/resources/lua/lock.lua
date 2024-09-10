if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then
  -- Check if the value was actually set
  if redis.call('get', KEYS[1]) == ARGV[1] then
    -- If it matches, set the expiration time
    return redis.call('expire', KEYS[1], ARGV[2])
  else
    -- If it doesn't match, return 0 indicating failure
    return 0
  end
end
