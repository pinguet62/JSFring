package fr.pinguet62.jsfring.gui.jasperreport;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.export.HtmlResourceHandler;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.Graphics2DExporterOutput;
import net.sf.jasperreports.export.HtmlExporterOutput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.WriterExporterOutput;

/**
 * Implements all super-interface of {@link ExporterOutput} used by different
 * implementations of {@link JRAbstractExporter}. <br>
 * This class can be used by
 * {@link JRAbstractExporter#setExporterOutput(ExporterOutput)} on different
 * implementations of {@link JRAbstractExporter}.
 * <p>
 * Use {@link OutputStream} to store data.
 *
 * @see OutputStreamExporterOutput
 * @see WriterExporterOutput
 * @see HtmlExporterOutput
 */
public class GeneralExporterOutput implements OutputStreamExporterOutput,
        WriterExporterOutput, HtmlExporterOutput, Graphics2DExporterOutput {

    private final OutputStream outputStream;

    /** @param outputStream The {@link #outputStream}. */
    public GeneralExporterOutput(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /** @see OutputStream#close() */
    @Override
    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new JRRuntimeException(e);
        }
    }

    /** @throws UnsupportedOperationException Not implemented */
    @Override
    public String getEncoding() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /** @throws UnsupportedOperationException Not implemented */
    @Override
    public HtmlResourceHandler getFontHandler() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /** @throws UnsupportedOperationException Not implemented */
    @Override
    public Graphics2D getGraphics2D() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /** @throws UnsupportedOperationException Not implemented */
    @Override
    public HtmlResourceHandler getImageHandler() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /** @return {@link #outputStream} */
    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /** @throws UnsupportedOperationException Not implemented */
    @Override
    public HtmlResourceHandler getResourceHandler() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Get the {@link Writer} by converting the {@link OutputStream}.
     *
     * @return The {@link Writer}.
     * @see OutputStreamWriter
     */
    @Override
    public Writer getWriter() {
        return new OutputStreamWriter(outputStream);
    }

}