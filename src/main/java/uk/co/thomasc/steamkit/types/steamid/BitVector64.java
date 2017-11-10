package uk.co.thomasc.steamkit.types.steamid;

public class BitVector64 {
    private Long data;

    public BitVector64() {
    }

    public BitVector64(long value) {
        data = value;
    }

    public long getMask(short bitoffset, long valuemask) {
        return (data >> bitoffset) & valuemask;
    }

    public void setMask(short bitoffset, long valuemask, long value) {
        data = (data & ~(valuemask << bitoffset)) | ((value & valuemask) << bitoffset);
    }

    public Long getData() {
        return this.data;
    }

    public void setData(final Long data) {
        this.data = data;
    }
}
