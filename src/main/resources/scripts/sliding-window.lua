local key = KEYS[1]

local now = tonumber(ARGV[1])
local window = tonumber(ARGV[2])
local limit = tonumber(ARGV[3])
local member = ARGV[4]

-- Remove expired requests
redis.call('ZREMRANGEBYSCORE', key, '-inf', now - window)

-- Count remaining requests
local count = redis.call('ZCARD', key)

-- Reject if limit exceeded
if count >= limit then
    return 0
end

-- Add current request
redis.call('ZADD', key, now, member)

-- Keep the key for window duration
redis.call('EXPIRE', key, math.ceil(window / 1000))

return 1