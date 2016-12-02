import feedparser
import datetime


def rss_info(url, number_of_entries=5):
    d = feedparser.parse(url)
    json1 = {}
    count = 0
    for post in d.entries:
        entry = {}
        if count <= number_of_entries:
            entry['title'] = post.title.encode('utf-8')
            entry['link'] = post.link
            entry['date'] = post.published
            entry['description'] = post.description.encode('utf-8')
            json1[count] = entry
            count += 1

    return json1


def busca_rss(url, colles, number_of_entries=5):
    d = feedparser.parse(url)
    json1 = {}
    count = 0
    for post in d.entries:
        for colla in colles:
            entry = {}
            post_encoded = post.description.encode('utf-8')
            if post_encoded.find(colla) != -1:
                if count <= number_of_entries:
                    entry['title'] = post.title.encode('utf-8')
                    entry['link'] = post.link
                    entry['date'] = post.published
                    entry['description'] = post.description.encode('utf-8')
                    json1[count] = entry
                    count += 1

    return json1