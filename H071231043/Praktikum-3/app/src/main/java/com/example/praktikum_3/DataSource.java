package com.example.praktikum_3;

import com.example.praktikum_3.Post;
import com.example.praktikum_3.R;
import com.example.praktikum_3.Highlight;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataSource {
    public static ArrayList<Post> getDummyPosts(String packageName) {
        ArrayList<Post> posts = new ArrayList<>();

        posts.add(new Post("android.resource://" + packageName + "/" + R.drawable.pict1, "friendsss"));
        posts.add(new Post("android.resource://" + packageName + "/" + R.drawable.pict2, "trio"));
        posts.add(new Post("android.resource://" + packageName + "/" + R.drawable.pict3, "hellooo"));
        posts.add(new Post("android.resource://" + packageName + "/" + R.drawable.pict4, "gangss"));
        posts.add(new Post("android.resource://" + packageName + "/" + R.drawable.pict5, "sweet"));

        return posts;
    }
    public static ArrayList<FeedPost> getDummyFeedPosts(String packageName) {
        ArrayList<FeedPost> feedPosts = new ArrayList<>();
        Map<String, UserProfile> profiles = getUserProfiles(packageName);

        feedPosts.add(new FeedPost(
                "android.resource://" + packageName + "/" + R.drawable.pict2,
                "Saatnya beraksi! #InfinityAndBeyond #Adventures",
                "Woody",
                profiles.get("Woody").getProfileImageUrl(),
                246,
                "2 jam yang lalu"
        ));

        feedPosts.add(new FeedPost(
                "android.resource://" + packageName + "/" + R.drawable.pict1,
                "Waktu untuk mengeksplorasi dunia baru!  #SpaceExplorer #BuzzVibes",
                "buzz",
                profiles.get("buzz").getProfileImageUrl(),
                512,
                "45 menit yang lalu"
        ));

        feedPosts.add(new FeedPost(
                "android.resource://" + packageName + "/" + R.drawable.pict5,
                "#MorningAdventure",
                "buzz",
                profiles.get("buzz").getProfileImageUrl(),
                189,
                "Baru saja"
        ));

        feedPosts.add(new FeedPost(
                "android.resource://" + packageName + "/" + R.drawable.pict3,
                "#JessieVibes",
                "jessie",
                profiles.get("jessie").getProfileImageUrl(),
                328,
                "3 jam yang lalu"
        ));

        feedPosts.add(new FeedPost(
                "android.resource://" + packageName + "/" + R.drawable.pict4,
                "Kerja keras di tengah petualangan!  #Workation #RexLife",
                "rex",
                profiles.get("rex").getProfileImageUrl(),
                417,
                "Kemarin"
        ));

        return feedPosts;
    }

    public static Map<String, UserProfile> getUserProfiles(String packageName) {
        Map<String, UserProfile> profiles = new HashMap<>();

        profiles.put("Woody", new UserProfile(
                "Woody",
                "Sheriff Woody Pride",
                "Sheriff | Petualang sejati 🌟 | Menggenggam keberanian ",
                "android.resource://" + packageName + "/" + R.drawable.woody,
                1,
                1254,
                876
        ));

        profiles.put("buzz", new UserProfile(
                "buzz",
                "Buzz Lightyear",
                "Pahlawan galaksi | Penjelajah angkasa luar  | Berani tanpa batas",
                "android.resource://" + packageName + "/" + R.drawable.buzz,
                2,
                3567,
                512
        ));

        profiles.put("jessie", new UserProfile(
                "jessie",
                "Jessie",
                "Petualang | Penunggang kuda 🐎 | Penuh semangat dan keberanian",
                "android.resource://" + packageName + "/" + R.drawable.jessie,
                1,
                5432,
                423
        ));

        profiles.put("rex", new UserProfile(
                "rex",
                "Rex",
                "Programmer | Pecinta buku  | Penjelajah digital ",
                "android.resource://" + packageName + "/" + R.drawable.rex,
                1,
                2341,
                654
        ));

        return profiles;
    }

    public static ArrayList<Highlight> getDummyHighlights() {
        ArrayList<Highlight> highlights = new ArrayList<>();
        highlights.add(new Highlight(R.drawable.woody, "woody"));
        highlights.add(new Highlight(R.drawable.buzz, "buzz"));
        highlights.add(new Highlight(R.drawable.rex, "rex"));
        highlights.add(new Highlight(R.drawable.jessie, "jessie"));
        highlights.add(new Highlight(R.drawable.pict1, "us"));
        highlights.add(new Highlight(R.drawable.pict2, "friend"));
        highlights.add(new Highlight(R.drawable.pict3, "firend"));
        return highlights;
    }

    public static ArrayList<Highlight> getUserProfileHighlights(String username) {
        ArrayList<Highlight> highlights = new ArrayList<>();
        switch (username) {
            case "woody":
                highlights.add(new Highlight(R.drawable.pict1, "Cowboy Adventures"));
                highlights.add(new Highlight(R.drawable.pict2, "Sheriff Life"));
                break;
            case "buzz":
                highlights.add(new Highlight(R.drawable.pict1, "Galactic Missions"));
                highlights.add(new Highlight(R.drawable.pict5, "Space Travel"));
                break;
            case "jessie":
                highlights.add(new Highlight(R.drawable.pict5, "Cowgirl Adventures"));
                break;
            case "rex":
                highlights.add(new Highlight(R.drawable.pict3, "Adventures"));
                break;
            default:
                highlights.add(new Highlight(R.drawable.pict1, "highlights"));
                break;
        }
        return highlights;
    }

}

