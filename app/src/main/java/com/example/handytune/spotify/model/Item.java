package com.example.handytune.spotify.model;

import com.example.handytune.spotify.model.artist.Album;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    @SerializedName("album")
    @Expose
    private Album album;
    @SerializedName("collaborative")
    @Expose
    private Boolean collaborative;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("tracks")
    @Expose
    private Tracks tracks;
    @SerializedName("public")
    @Expose
    private Boolean _public;
    @SerializedName("snapshot_id")
    @Expose
    private String snapshotId;
    @SerializedName("external_urls")
    @Expose
    private ExternalUrls externalUrls;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("uri")
    @Expose
    private String uri;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Boolean getCollaborative() { return collaborative; }

    public void setCollaborative(Boolean collaborative) { this.collaborative = collaborative; }

    public String getHref() { return href; }

    public void setHref(String href) { this.href = href; }

    public Boolean getPublic() { return _public; }

    public void setPublic(Boolean _public) { this._public = _public; }

    public String getSnapshotId() { return snapshotId; }

    public void setSnapshotId(String snapshotId) { this.snapshotId = snapshotId; }

    public Tracks getTracks() { return tracks; }

    public void setTracks(Tracks tracks) { this.tracks = tracks; }

    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrls externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
