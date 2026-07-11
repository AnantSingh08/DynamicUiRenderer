package com.dynamicui.shared.data.mapper

import com.dynamicui.shared.data.dto.feed.FeedDto
import com.dynamicui.shared.domain.model.Feed

interface FeedMapper :
    Mapper<FeedDto, Feed>